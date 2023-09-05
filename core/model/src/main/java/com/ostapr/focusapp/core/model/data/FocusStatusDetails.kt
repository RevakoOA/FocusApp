package com.ostapr.focusapp.core.model.data

import java.time.LocalDateTime

data class FocusStatusDetails(
    val id: Long,
    val dateTime: LocalDateTime,
    val installedApps: List<InstalledAppInfo>
)