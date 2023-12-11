package com.carlosolimpio.cstv.presentation.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"

fun String.parseDate(): String {
    val dateTime = convertFromUTCToLocalTime(this)
    val ptBrLocale = Locale("pt", "BR")

    val isToday = dateTime.toLocalDate().equals(LocalDate.now())
    val isInNextWeek = isInNextWeek(dateTime.toLocalDate())

    val formatter = when {
        isToday -> DateTimeFormatter.ofPattern("'Hoje, 'HH:mm", ptBrLocale)
        isInNextWeek -> DateTimeFormatter.ofPattern("EEE', 'HH:mm", ptBrLocale)
        else -> DateTimeFormatter.ofPattern("dd.MM HH:mm", ptBrLocale)
    }

    val result = dateTime.format(formatter)

    return if (isInNextWeek) {
        result.capitalize().replace(".", "")
    } else {
        result
    }
}

fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.uppercaseChar() }
}

private fun convertFromUTCToLocalTime(text: String): ZonedDateTime {
    val utcDateTime = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(UTC_PATTERN))
    val utcZonedDateTime = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC"))
    return utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
}

private fun isInNextWeek(dateToCheck: LocalDate): Boolean {
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.SUNDAY)
    val endOfWeek = startOfWeek.plusDays(6)

    return !dateToCheck.isBefore(startOfWeek) && !dateToCheck.isAfter(endOfWeek)
}
