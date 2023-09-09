package com.ostapr.focusapp.core.network.model

import com.ostapr.focusapp.core.model.data.Minutes
import okhttp3.ResponseBody
import com.ostapr.focusapp.core.model.data.DelayInfo as CoreDelayInfo
import org.json.JSONArray

internal data class DelayInfo(val minutes: Minutes) {

    fun toCoreModel() = CoreDelayInfo(minutes)

    companion object {
        private const val INTERVAL_IN_MINUTES = "intervalInMinutes"

        fun parseFromResponse(response: ResponseBody): DelayInfo {
            val jsonObject = JSONArray(response.string()).getJSONObject(0)
            val minutesDelay = jsonObject.getInt(INTERVAL_IN_MINUTES)

            return DelayInfo(Minutes(minutesDelay))
        }
    }
}