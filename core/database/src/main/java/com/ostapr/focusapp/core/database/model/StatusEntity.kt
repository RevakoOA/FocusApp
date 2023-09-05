package com.ostapr.focusapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity()
data class StatusEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dateTime: LocalDateTime,
)