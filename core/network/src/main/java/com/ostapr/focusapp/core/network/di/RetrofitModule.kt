package com.ostapr.focusapp.core.network.di

import com.ostapr.focusapp.core.network.FocusAppNetworkDataSource
import com.ostapr.focusapp.core.network.retrofit.RetrofitFocusAppNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitModule {
    @Binds
    fun providesNetworkDataSource(impl: RetrofitFocusAppNetwork): FocusAppNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}