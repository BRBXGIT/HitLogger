package com.example.hitlogger.presentation.home.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.hitlogger.presentation.common.theme.HitLoggerIcons
import com.example.hitlogger.presentation.home.screen.HomeIntent

@Composable
fun DevicesFab(onIntent: (HomeIntent) -> Unit) {
    FloatingActionButton(
        onClick = { onIntent(HomeIntent.ToggleDevicesBS) }
    ) {
        Icon(
            painter = painterResource(HitLoggerIcons.Bluetooth),
            contentDescription = null
        )
    }
}