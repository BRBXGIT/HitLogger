package com.example.hitlogger.presentation.home.screen

import java.time.LocalDate

sealed interface HomeIntent {
    data class OnSelectDate(val date: LocalDate): HomeIntent

    data object OnPlusDay: HomeIntent
    data object OnMinusDay: HomeIntent
}