package com.ostapr.focusapp.core.network

import com.ostapr.focusapp.core.model.data.DelayInfo

interface FocusAppNetworkDataSource {

    suspend fun getUpdateDelay(): DelayInfo
}