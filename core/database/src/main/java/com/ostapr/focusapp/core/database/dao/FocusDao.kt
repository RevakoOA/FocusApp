package com.ostapr.focusapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.relations.StatusDetails
import java.time.Duration
import java.time.LocalDateTime

@Dao
interface FocusDao {

    @Insert
    suspend fun addStatus(statusDetails: StatusDetails)

    /**
     * Before calling this, [deleteOldStatuses] should be called,
     * so all remaining statuses are recent statuses.
     */
    @Query("SELECT * FROM StatusEntity")
    suspend fun getStatuses(now: LocalDateTime, duration: Duration): List<StatusDetails>


    suspend fun deleteOldStatuses(now: LocalDateTime, duration: Duration): Int {
        val from = now - duration
        return removeOldStatus(from)
    }

    @Query("DELETE FROM StatusEntity WHERE dateTime < :from")
    suspend fun removeOldStatus(from: LocalDateTime): Int
}