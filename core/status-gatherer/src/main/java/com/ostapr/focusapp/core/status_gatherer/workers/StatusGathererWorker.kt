package com.ostapr.focusapp.core.status_gatherer.workers

import android.content.Context
import android.content.pm.PackageManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.ostapr.focusapp.core.common.dispatchers.Dispatcher
import com.ostapr.focusapp.core.common.dispatchers.FocusAppDispatchers.IO
import com.ostapr.focusapp.core.data.repositories.DelayRepository
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import com.ostapr.focusapp.core.model.data.InstalledAppInfo
import com.ostapr.focusapp.core.model.data.Minutes
import com.ostapr.focusapp.core.status_gatherer.initializers.GathererInitializer
import com.ostapr.focusapp.core.status_gatherer.initializers.gathererForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.concurrent.TimeUnit

@HiltWorker
class StatusGathererWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val delayRepo: DelayRepository,
    private val statusesRepo: StatusesRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(context, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        applicationContext.gathererForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        // TODO(ostapr) store previous delay and use if request failed. (see DataStore)
        val interval = delayRepo.getDelay()

        val installedApps = fetchNonSystemApps()

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val status = FocusStatusDetails(now, installedApps)
         statusesRepo.apply {
            removeOldStatuses()
            addStatus(status)
        }

        GathererInitializer.initializeNextRequest(applicationContext, interval.minutes)

        Result.success()
    }

    private fun fetchNonSystemApps(): List<InstalledAppInfo> {
        val pm = applicationContext.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val nonSystemPackages = packages.filter { packageInfo ->
            pm.getLaunchIntentForPackage(packageInfo.packageName) != null
        }

        return nonSystemPackages.map { packageInfo ->
            val appName = pm.getApplicationLabel(packageInfo).toString()
            // TODO(ostapr) fetch drawable, store to Internal disk memory.
            //  Will allow to access icon even if the app is deleted. Currently use packageName.

            InstalledAppInfo(appName, packageInfo.packageName)
        }
    }

    companion object {
        /**
         * Expedited one time work to gather status.
         */
        fun scheduleRequest(delay: Minutes) = OneTimeWorkRequestBuilder<StatusGathererWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .apply {
                if (delay.toLong() > 0) {
                    setInitialDelay(delay.toLong(), TimeUnit.MINUTES)
                }
            }
            .build()
    }
}