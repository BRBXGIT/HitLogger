package com.example.hitlogger.presentation.common.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class HitLoggerAnimationTokens(
    val extraShort: Int = 100,
    val short: Int = 300,
    val medium: Int = 500,
    val long: Int = 700,
    val extraLong: Int = 900
)

data class HitLoggerDimensions(
    // Paddings
    val paddingExtraSmall: Dp = 4.dp,
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 20.dp,
    val paddingExtraLarge: Dp = 24.dp,

    // Spacers
    val spacingExtraSmall: Dp = 4.dp,
    val spacingSmall: Dp = 8.dp,
    val spacingMedium: Dp = 16.dp,
    val spacingLarge: Dp = 20.dp,
    val spacingExtraLarge: Dp = 24.dp,
)


val LocalDimensions = staticCompositionLocalOf { HitLoggerDimensions() }

val LocalAnimationTokens = staticCompositionLocalOf { HitLoggerAnimationTokens() }