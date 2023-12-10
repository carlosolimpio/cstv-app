package com.carlosolimpio.cstv.presentation.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"

fun String.parseDate(): String {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(UTC_PATTERN))
    val isToday = dateTime.toLocalDate().equals(LocalDate.now())
    val isInNextWeek = isInNextWeek(dateTime.toLocalDate())
    val ptBrLocale = Locale("pt", "BR")

    val formatter = if (isToday) {
        DateTimeFormatter.ofPattern("'Hoje, 'HH:mm", ptBrLocale)
    } else if (isInNextWeek) {
        DateTimeFormatter.ofPattern("EEE', 'HH:mm", ptBrLocale)
    } else {
        DateTimeFormatter.ofPattern("dd.MM HH:mm", ptBrLocale)
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

private fun isInNextWeek(dateToCheck: LocalDate): Boolean {
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.SUNDAY)
    val endOfWeek = startOfWeek.plusDays(6)

    return !dateToCheck.isBefore(startOfWeek) && !dateToCheck.isAfter(endOfWeek)
}
