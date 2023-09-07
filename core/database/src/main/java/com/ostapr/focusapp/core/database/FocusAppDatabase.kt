package com.ostapr.focusapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ostapr.focusapp.core.database.dao.FocusDao
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.database.model.StatusEntity

@Database(
    entities = [
        StatusEntity::class,
        InstalledAppEntity::class,
        StatusAppCrossRef::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    LocalDateTimeConverter::class,
)
abstract class FocusAppDatabase : RoomDatabase() {
    abstract fun focusDao(): FocusDao
}