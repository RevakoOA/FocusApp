package com.ostapr.focusapp.core.model.data

import java.time.LocalDateTime

data class FocusStatusItem(
    val id: Long,
    val dateTime: LocalDateTime,
    val appsCount: Int,
    val isFocused: Boolean,
)