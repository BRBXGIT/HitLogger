package com.example.hitlogger.presentation.home.screen

import androidx.compose.runtime.Immutable
import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import com.example.hitlogger.domain.models.hit.Hit
import com.example.hitlogger.presentation.home.components.DateUtils
import java.time.LocalDateTime

@Immutable
data class HomeState(

    // --- Hits ---
    val hits: List<Hit> = emptyList(),

    // --- Date ---
    val date: String = DateUtils().format(LocalDateTime.now().toLocalDate()),

    // --- Devices ---
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),

    // --- Ui ---
    val isDevicesBSVisible: Boolean = false
)