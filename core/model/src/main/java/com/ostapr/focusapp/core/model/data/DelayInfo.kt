package com.ostapr.focusapp.core.model.data

import java.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@JvmInline
value class Minutes(private val value: Int) {
    fun toLong() = value.toLong()

    fun toDuration(): Duration = value.minutes.toJavaDuration()
}

data class DelayInfo(val minutes: Minutes)