package com.ostapr.focusapp.core.status_gatherer.initializers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.WorkManager
import com.ostapr.focusapp.core.model.data.Minutes
import com.ostapr.focusapp.core.status_gatherer.workers.StatusGathererWorker

object GathererInitializer {

    /**
     * Each request will make another request after updating repeat interval.
     */
    fun initializeStatusGatherer(context: Context, interval: Minutes, policy: ExistingPeriodicWorkPolicy = KEEP) {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(GathererWorkName, policy, StatusGathererWorker.scheduleRequest(interval))
        }
    }

    private const val GathererWorkName = "GathererWorkName"
}