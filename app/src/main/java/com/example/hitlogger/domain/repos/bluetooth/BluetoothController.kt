package com.example.hitlogger.domain.repos.bluetooth

import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()
}