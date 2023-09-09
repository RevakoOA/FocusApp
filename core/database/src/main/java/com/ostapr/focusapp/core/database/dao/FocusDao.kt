package com.ostapr.focusapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.relations.StatusDetails
import kotlinx.coroutines.flow.Flow
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
    @Transaction
    @Query("SELECT * FROM StatusEntity")
    fun getStatuses(): Flow<List<StatusDetails>>

    @Query("SELECT * FROM StatusEntity WHERE id = :id")
    fun getStatus(id: Long): Flow<StatusDetails>

    @Query("DELETE FROM StatusEntity WHERE dateTime < :fromDate")
    suspend fun deleteOldStatuses(fromDate: String)
}