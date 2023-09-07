package com.ostapr.focusapp.core.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ostapr.focusapp.core.database.model.InstalledAppEntity
import com.ostapr.focusapp.core.database.model.StatusEntity
import com.ostapr.focusapp.core.database.model.StatusAppCrossRef
import com.ostapr.focusapp.core.model.data.FocusStatusDetails


data class StatusDetails(
    @Embedded
    val status: StatusEntity,

    @Relation(
        associateBy = Junction(
            StatusAppCrossRef::class,
            parentColumn = "status_id",
            entityColumn = "app_id",
        ),
        parentColumn = "id",
        entityColumn = "id",
    )
    val installedApps: List<InstalledAppEntity>
) {
    fun convertToCoreStatusDetails() = FocusStatusDetails(
        status.dateTime, installedApps.map { it.convertToCoreInstalledApp() }
    )
}