package com.ostapr.focusapp.core.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.model.StatusesInstalledAppsCrossRef


data class StatusDetails(
    @Embedded
    val status: StatusEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "statusId",
        associateBy = Junction(StatusesInstalledAppsCrossRef::class)
    )
    val installedApps: List<InstalledAppEntity>
)