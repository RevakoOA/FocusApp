package com.ostapr.focusapp.core.data.di

import com.ostapr.focusapp.core.data.repositories.DelayRepository
import com.ostapr.focusapp.core.data.repositories.NetworkOnlyDelayRepo
import com.ostapr.focusapp.core.data.repositories.OfflineOnlyStatusesRepo
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Binds
    fun bindDelayRepository(impl: NetworkOnlyDelayRepo): DelayRepository

    @Binds
    fun bindStatusRepository(impl: OfflineOnlyStatusesRepo): StatusesRepository
}