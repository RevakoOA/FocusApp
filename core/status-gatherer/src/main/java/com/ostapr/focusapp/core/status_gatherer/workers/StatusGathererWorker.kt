package com.ostapr.focusapp.core.status_gatherer.workers

import android.content.Context
import android.content.pm.PackageManager
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.ostapr.focusapp.core.data.repositories.DelayRepository
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import com.ostapr.focusapp.core.model.data.InstalledAppInfo
import com.ostapr.focusapp.core.status_gatherer.initializers.GathererInitializer
import com.ostapr.focusapp.core.status_gatherer.initializers.gathererForegroundInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class StatusGathererWorker(
    @ApplicationContext appContext: Context,
    private val delayRepo: DelayRepository,
    private val statusesRepo: StatusesRepository,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        applicationContext.gathererForegroundInfo()

    override suspend fun doWork(): Result {
        // TODO(ostapr) store previous delay and use if request failed. (see DataStore)
        val interval = delayRepo.getDelay()

        val installedApps = fetchNonSystemApps()

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val status = FocusStatusDetails(now, installedApps)
        statusesRepo.apply  {
            removeOldStatuses()
            addStatus(status)
        }

        GathererInitializer.initializeNextRequest(applicationContext, interval.minutes)

        return Result.success()
    }

    private fun fetchNonSystemApps(): List<InstalledAppInfo> {
        val pm = applicationContext.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val nonSystemPackages = packages.filter { packageInfo ->
            pm.getLaunchIntentForPackage(packageInfo.packageName) != null
        }

        return nonSystemPackages.map {packageInfo ->
            val appName = pm.getApplicationLabel(packageInfo).toString()
            // TODO(ostapr) fetch drawable, store to Internal disk memory.
            //  Will allow to access icon even if the app is deleted. Currently use packageName.

            InstalledAppInfo(appName, packageInfo.packageName)
        }
    }
}