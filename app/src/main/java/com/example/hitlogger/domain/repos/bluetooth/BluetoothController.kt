package com.example.hitlogger.domain.repos.bluetooth

import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import com.example.hitlogger.domain.models.bluetooth.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()

    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult>

    fun closeConnection()
    fun release()
}