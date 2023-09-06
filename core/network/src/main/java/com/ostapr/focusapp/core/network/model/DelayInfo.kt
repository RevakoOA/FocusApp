package com.ostapr.focusapp.core.network.model

import org.json.JSONArray

@JvmInline
value class Minutes(private val value: Int)

data class DelayInfo(val minutes: Minutes) {
    companion object {
        private const val INTERVAL_IN_MINUTES = "intervalInMinutes"

        fun parseFromJsonArray(array: JSONArray): DelayInfo {
            val jsonObject = array.getJSONObject(0)
            val minutesDelay = jsonObject.getInt(INTERVAL_IN_MINUTES)

            return DelayInfo(Minutes(minutesDelay))
        }
    }
}