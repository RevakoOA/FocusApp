package com.ostapr.focusapp.core.model.data

import kotlinx.datetime.LocalDateTime

data class FocusStatusDetails(
    val dateTime: LocalDateTime,
    val installedApps: List<InstalledAppInfo>
) {
    val appsCount: Int
        get() = installedApps.size

    val isFocused: Boolean
        get() = appsCount <= APPS_FOCUS_LIMIT && !containsApp(INSTAGRAM_NAME)

    fun containsApp(name: String): Boolean = installedApps.any { it.appName.equals(name, ignoreCase = true) }

    companion object {
        const val APPS_FOCUS_LIMIT = 15
        const val INSTAGRAM_NAME = "Instagram"
    }
}