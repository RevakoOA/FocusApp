package com.ostapr.focusapp.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class InstalledAppEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val imageUri: String,
    val name: String
)