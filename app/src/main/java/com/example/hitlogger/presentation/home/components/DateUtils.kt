package com.example.hitlogger.presentation.home.components

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())

    fun format(date: LocalDate): String = date.format(formatter)

    fun parse(dateString: String): LocalDate = LocalDate.parse(dateString, formatter)
}