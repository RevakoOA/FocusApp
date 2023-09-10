package com.ostapr.focusapp.core.status_gatherer.workers

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo.FLAG_SYSTEM
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingPeriodicWorkPolicy.UPDATE
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
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
        val installedApps = fetchNonSystemApps()

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val status = FocusStatusDetails(id = 0, now, installedApps)

        statusesRepo.apply {
            removeOldStatuses()
            addStatus(status)
        }

        try {
            val interval = delayRepo.getDelay()
            GathererInitializer.initializeStatusGatherer(
                applicationContext,
                interval.minutes,
                policy = UPDATE
            )
        } catch (e: Exception) {
            // do not update period as new data are not available
        }

        Result.success()
    }

    /**
     *  Using package manager gather list of non system apps.
     *
     *  Filtering logic:
     *  A good rule of thumb is if the app does not contain the main activity,
     *  it is not a user-facing application and could be considered a system app.
     *
     * @returns list of non system apps.
     */
    private fun fetchNonSystemApps(): List<InstalledAppInfo> {
        val pm = applicationContext.packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolvedNonSystemAppsInfos = if (Build.VERSION.SDK_INT >= TIRAMISU) {
            pm.queryIntentActivities(
                mainIntent,
                PackageManager.ResolveInfoFlags.of(0L)
            )
        } else {
            pm.queryIntentActivities(mainIntent, 0)
        }

        return resolvedNonSystemAppsInfos.map { appInfo ->
            val resources = pm.getResourcesForApplication(appInfo.activityInfo.applicationInfo)

            val appName = if (appInfo.activityInfo.labelRes != 0) {
                // getting proper label from resources
                resources.getString(appInfo.activityInfo.labelRes)
            } else {
                // getting it out of app info - equivalent to context.packageManager.getApplicationInfo
                appInfo.activityInfo.applicationInfo.loadLabel(pm).toString()
            }

            // TODO(ostapr) fetch drawable, store to Internal disk memory.
            //  Will allow to access icon even if the app is deleted. Currently use packageName.
            // val drawable = appInfo.activityInfo.applicationInfo.loadIcon(pm)

            InstalledAppInfo(appName, appInfo.activityInfo.packageName)
        }
    }

    companion object {
        /** Periodic work request to gather status. */
        fun scheduleRequest(interval: Minutes) =
            PeriodicWorkRequestBuilder<StatusGathererWorker>(interval.toDuration())
                .build()
    }
}