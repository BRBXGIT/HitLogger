package com.example.hitlogger.presentation.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun HitLoggerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dimensions: HitLoggerDimensions = HitLoggerDimensions(),
    animationTokens: HitLoggerAnimationTokens = HitLoggerAnimationTokens(),
    content: @Composable () -> Unit
) {
    val colorScheme = when(darkTheme) {
        true -> DarkLavenderScheme
        false -> LightLavenderScheme
    }

    CompositionLocalProvider(
        LocalDimensions provides dimensions,
        LocalAnimationTokens provides animationTokens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Quick access to theme values
val mColors @Composable get() = MaterialTheme.colorScheme
val mTypography @Composable get() = MaterialTheme.typography
val mShapes @Composable get() = MaterialTheme.shapes
val mMotionScheme @Composable get() = MaterialTheme.motionScheme
val mDimens @Composable get() = LocalDimensions.current
val mAnimationTokens @Composable get() = LocalAnimationTokens.current