package com.ostapr.focusapp.core.data.repositories

import com.ostapr.focusapp.core.model.data.DelayInfo

interface DelayRepository {
    suspend fun getDelay(): DelayInfo
}