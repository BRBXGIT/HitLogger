package com.example.hitlogger.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Hit(
    val id: Int = 0,
    val hitType: HitType = HitType.Unknown,
    val date: String = "01.03.2026 20:22:22"
)
