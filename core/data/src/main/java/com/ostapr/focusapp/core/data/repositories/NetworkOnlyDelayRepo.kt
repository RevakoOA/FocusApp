package com.ostapr.focusapp.core.data.repositories

import com.ostapr.focusapp.core.model.data.DelayInfo
import com.ostapr.focusapp.core.network.FocusAppNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkOnlyDelayRepo @Inject constructor(
    private val networkDataSource: FocusAppNetworkDataSource
): DelayRepository {
    override suspend fun getDelay(): DelayInfo = networkDataSource.getUpdateDelay()
}