package com.ostapr.focusapp.core.network

import com.ostapr.focusapp.core.network.model.DelayInfo


interface FocusAppNetworkDataSource {

    suspend fun getUpdateDelay(): DelayInfo
}