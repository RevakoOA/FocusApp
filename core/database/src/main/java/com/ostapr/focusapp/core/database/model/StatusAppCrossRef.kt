package com.ostapr.focusapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "statuses_apps",
    primaryKeys = ["status_id", "app_id"],
    foreignKeys = [
        ForeignKey(
            entity = StatusEntity::class,
            parentColumns = ["id"],
            childColumns = ["status_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = InstalledAppEntity::class,
            parentColumns = ["id"],
            childColumns = ["app_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["status_id"]),
        Index(value = ["app_id"]),
    ]
)
data class StatusAppCrossRef(
    @ColumnInfo(name = "status_id")
    val statusId: Long,

    @ColumnInfo("app_id")
    val appId: Long,
) {

    companion object {
        fun createFrom(statusId: Long, appId: Long) = StatusAppCrossRef(statusId, appId)
    }
}