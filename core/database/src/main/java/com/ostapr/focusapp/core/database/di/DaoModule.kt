package com.ostapr.focusapp.core.database.di

import com.ostapr.focusapp.core.database.FocusAppDatabase
import com.ostapr.focusapp.core.database.dao.FocusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideFocusDao(db: FocusAppDatabase): FocusDao = db.focusDao()
}