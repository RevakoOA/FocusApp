package com.ostapr.focusapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.ostapr.focusapp.core.database.FocusAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FocusAppDatabase =
        Room.databaseBuilder(
            appContext,
            FocusAppDatabase::class.java,
            "focus-database"
        ).build()
}