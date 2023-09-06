package com.ostapr.focusapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ostapr.focusapp.core.model.data.InstalledAppInfo

@Entity
data class InstalledAppEntity(
    @PrimaryKey
    val name: String,
    val imageUri: String,
) {
    fun convertToCoreInstalledApp() = InstalledAppInfo(name, imageUri)

    companion object {
        fun createFromCoreInstalledApp(core: InstalledAppInfo) =
            InstalledAppEntity(name = core.appName, imageUri = core.iconUri)
    }
}