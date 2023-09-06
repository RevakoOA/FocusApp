package com.ostapr.focusapp.core.data.repositories


import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

interface StatusesRepository {

    suspend fun addStatus(statusDetails: FocusStatusDetails)

    suspend fun removeOldAndFetchRecentStatuses(
        now: Instant = Clock.System.now(),
        recentDuration: Duration
    ): List<FocusStatusDetails>
}