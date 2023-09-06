package com.ostapr.focusapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.relations.StatusDetails
import kotlinx.datetime.Instant
import kotlin.time.Duration

@Dao
interface FocusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(statusDetails: StatusEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledApps(apps: List<InstalledAppEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatusWithApp(statusWithAppList: List<StatusAppCrossRef>)

    /**
     * Before calling this, [deleteOldStatuses] should be called,
     * so all remaining statuses are recent statuses.
     */
    @Query("SELECT * FROM StatusEntity")
    suspend fun getStatuses(now: Instant, duration: Duration): List<StatusDetails>


    suspend fun deleteOldStatuses(now: Instant, duration: Duration): Int {
        val from = now - duration
        return removeOldStatus(from)
    }

    @Query("DELETE FROM StatusEntity WHERE dateTime < :from")
    suspend fun removeOldStatus(from: Instant): Int
}