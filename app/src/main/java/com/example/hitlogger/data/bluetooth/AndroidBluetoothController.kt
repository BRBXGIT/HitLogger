package com.example.hitlogger.data.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.example.hitlogger.di.Dispatcher
import com.example.hitlogger.di.HitLoggerDispatcher
import com.example.hitlogger.domain.models.bluetooth.BluetoothDeviceDomain
import com.example.hitlogger.domain.models.bluetooth.ConnectionResult
import com.example.hitlogger.domain.repos.bluetooth.BluetoothController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@SuppressLint("MissingPermission")
class AndroidBluetoothController @Inject constructor(
    private val context: Context,
    @param:Dispatcher(HitLoggerDispatcher.Io) private val dispatcherIo: CoroutineDispatcher
): BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _isConnected = MutableStateFlow(false)
    override val isConnected = _isConnected.asStateFlow()

    private val _scannedDevices = MutableStateFlow(emptyList<BluetoothDeviceDomain>())
    override val scannedDevices = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow(emptyList<BluetoothDeviceDomain>())
    override val pairedDevices = _pairedDevices.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors = _errors.asSharedFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toDomain()
            if (newDevice in devices) devices else devices + newDevice
        }
    }

    private val bluetoothStateReceiver = BluetoothStateReceiver { isConnected, device ->
        if (bluetoothAdapter?.bondedDevices?.contains(device) == true) {
            _isConnected.update { isConnected }
        } else {
            CoroutineScope(dispatcherIo).launch {
                _errors.tryEmit("Can't connect to ad non paired device")
            }
        }
    }

    private var currentClientSocket: BluetoothSocket? = null

    init {
        updatePairedDevices()
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
    }

    override fun startDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun connectToDevice(device: BluetoothDeviceDomain): Flow<ConnectionResult> {
        return flow {
            if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            currentClientSocket = bluetoothAdapter
                ?.getRemoteDevice(device.address)
                ?.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_UUID))

            stopDiscovery()

            currentClientSocket?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.ConnectionEstablished)
                } catch (_: IOException) {
                    socket.close()
                    currentClientSocket = null
                    emit(ConnectionResult.Error("Connection was interrupted"))
                }
            }
        }.onCompletion {
            closeConnection()
        }.flowOn(dispatcherIo)
    }

    override fun closeConnection() {
        currentClientSocket?.close()
        currentClientSocket = null
    }

    override fun release() {
        context.unregisterReceiver(bluetoothStateReceiver)
        context.unregisterReceiver(foundDeviceReceiver)
        closeConnection()
    }

    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
        bluetoothAdapter!!.bondedDevices
            .map { it.toDomain() }
            .also { new -> _pairedDevices.update { new } }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun BluetoothDevice.toDomain(): BluetoothDeviceDomain {
        return BluetoothDeviceDomain(
            name = this.name,
            address = this.address
        )
    }

    companion object {
        const val SERVICE_UUID = "9e402d32-ffea-40ae-8585-131e28901195"
    }
}