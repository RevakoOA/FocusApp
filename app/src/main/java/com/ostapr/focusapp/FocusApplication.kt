package com.ostapr.focusapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ostapr.focusapp.core.model.data.Minutes
import com.ostapr.focusapp.core.status_gatherer.initializers.GathererInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * [Application] class for Focus App.
 */
@HiltAndroidApp
class FocusApplication : Application(), Configuration.Provider {

    @Inject
    internal lateinit var hiltWorkerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        GathererInitializer.initializeStatusGatherer(this, Minutes(0))
    }
}