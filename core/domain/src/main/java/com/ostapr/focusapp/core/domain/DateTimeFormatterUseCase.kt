package com.ostapr.focusapp.core.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeFormatterUseCase @Inject constructor() {

    private val uiDateFormatter = DateTimeFormatter.ofPattern("dd/MM/uu, HH:mm")

    operator fun invoke(localDateTime: LocalDateTime): String =
        uiDateFormatter.format(localDateTime.toJavaLocalDateTime())
}