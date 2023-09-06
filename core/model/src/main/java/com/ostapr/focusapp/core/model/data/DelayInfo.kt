package com.ostapr.focusapp.core.model.data
@JvmInline
value class Minutes(private val value: Int) {
    fun toLong() = value.toLong()
}

data class DelayInfo(val minutes: Minutes)