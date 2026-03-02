package com.example.hitlogger.presentation.home.screen

import androidx.compose.runtime.Immutable
import com.example.hitlogger.domain.models.hit.Hit
import com.example.hitlogger.presentation.home.components.DateUtils
import java.time.LocalDateTime

@Immutable
data class HomeState(
    val hits: List<Hit> = emptyList(),
    val date: String = DateUtils().format(LocalDateTime.now().toLocalDate())
)