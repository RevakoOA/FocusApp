package com.ostapr.focusapp.core.data.repositories


import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

interface StatusesRepository {

    fun getStatuses(): Flow<List<FocusStatusDetails>>

    suspend fun addStatus(statusDetails: FocusStatusDetails)

    suspend fun removeOldStatuses(
        now: Instant = Clock.System.now(),
        recentDuration: Duration = 2.days
    )
}