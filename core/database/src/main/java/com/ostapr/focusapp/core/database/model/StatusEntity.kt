package com.ostapr.focusapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import kotlinx.datetime.LocalDateTime

@Entity()
data class StatusEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val dateTime: LocalDateTime,
) {
    companion object {
        fun createFromCoreStatusDetails(core: FocusStatusDetails) = StatusEntity(dateTime = core.dateTime)
    }
}