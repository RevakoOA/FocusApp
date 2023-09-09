package com.ostapr.focusapp.core.status_gatherer.initializers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.ostapr.focusapp.core.model.data.Minutes
import com.ostapr.focusapp.core.status_gatherer.workers.StatusGathererWorker
import java.util.concurrent.TimeUnit

object GathererInitializer {

    /**
     * Each request will make another request after updating repeat interval.
     */
    fun initializeNextRequest(context: Context, nextInterval: Minutes) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                GathererWorkName,
                ExistingWorkPolicy.KEEP,
                StatusGathererWorker.scheduleRequest(nextInterval)
            )
        }
    }

    private const val GathererWorkName = "GathererWorkName"
}