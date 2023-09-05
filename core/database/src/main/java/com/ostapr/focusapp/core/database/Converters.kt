package com.ostapr.focusapp.core.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun stringToLocalDateTime(dateTimeRepresentation: String?): LocalDateTime? =
        dateTimeRepresentation?.toLocalDateTime()

    @TypeConverter
    fun localDateTimeToString(dateTime: LocalDateTime?): String? = dateTime?.toString()

}