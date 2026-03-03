package com.example.hitlogger.presentation.home.screen

import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import java.time.LocalDate

sealed interface HomeIntent {

    // --- Date ---
    data class OnSelectDate(val date: LocalDate): HomeIntent
    data object OnPlusDay: HomeIntent
    data object OnMinusDay: HomeIntent

    // --- Ui ---
    data object ToggleDevicesBS: HomeIntent

    // Bluetooth
    data class OnDeviceClick(val device: BluetoothDevice): HomeIntent
}