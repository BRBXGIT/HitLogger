package com.example.hitlogger.domain.models.bluetooth

import androidx.compose.runtime.Immutable

typealias BluetoothDeviceDomain = BluetoothDevice

@Immutable
data class BluetoothDevice(
    val name: String? = null,
    val address: String = ""
)