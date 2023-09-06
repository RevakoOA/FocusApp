package com.ostapr.focusapp.core.model.data

import kotlinx.datetime.LocalDateTime

data class FocusStatusDetails(
    val dateTime: LocalDateTime,
    val installedApps: List<InstalledAppInfo>
)