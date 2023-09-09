package com.ostapr.focusapp.feature.status.model

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.ostapr.focusapp.core.model.data.FocusStatusDetails
import com.ostapr.focusapp.core.model.data.InstalledAppInfo
import kotlinx.datetime.LocalDateTime


data class UiStatusDetails(
    val id: Long,
    val dateTime: String,
    val apps: List<UiInstalledAppItem>,
    val isFocused: Boolean
) {
    val appsCount: Int
        get() = apps.size
}

data class UiInstalledAppItem(val name: String, val image: Drawable)