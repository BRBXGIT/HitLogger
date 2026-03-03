package com.example.hitlogger.presentation.home.screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import com.example.hitlogger.domain.models.bluetooth.ConnectionResult
import com.example.hitlogger.domain.repos.bluetooth.BluetoothController
import com.example.hitlogger.domain.repos.hit.HitRepo
import com.example.hitlogger.presentation.home.components.DateUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Stable
class HomeVM @Inject constructor(
    hitRepo: HitRepo,
    private val bluetoothController: BluetoothController
): ViewModel() {

    private val dateUtils = DateUtils()

    private val _state = MutableStateFlow(HomeState())

    private var deviceConnectionJob: Job? = null

    init {
        bluetoothController.isConnected.onEach { isConnected ->
            _state.update { it.copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)

        bluetoothController.errors.onEach { error ->
            _state.update { it.copy(errorMessage = error) }
        }.launchIn(viewModelScope)
    }

    val state = combine(
        flow = bluetoothController.scannedDevices,
        flow2 = bluetoothController.pairedDevices,
        flow3 = hitRepo.getHits(),
        flow4 = _state
    ) { scanned, paired, hits, state ->
        val dateString = dateUtils.format(state.date)

        val filteredHits = hits.filter { hit ->
            hit.date.startsWith(dateString)
        }

        state.copy(
            scannedDevices = scanned,
            pairedDevices = paired,
            hits = filteredHits,
            dateString = dateString
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HomeState()
    )

    // --- Intents ---
    fun sendIntent(intent: HomeIntent) {
        when(intent) {

            // --- Date ---
            HomeIntent.OnMinusDay -> _state.update { it.copy(date = it.date.minusDays(1)) }
            HomeIntent.OnPlusDay -> _state.update { it.copy(date = it.date.plusDays(1)) }
            is HomeIntent.OnSelectDate -> _state.update { it.copy(date = intent.date) }

            // --- Ui ---
            HomeIntent.ToggleDevicesBS -> {
                _state.update {
                    if (!it.isDevicesBSVisible) startScan() else stopScan()
                    it.copy(isDevicesBSVisible = !it.isDevicesBSVisible)
                }
            }

            // --- Bluetooth ---
            is HomeIntent.OnDeviceClick -> connectToDevice(intent.device)
        }
    }

    fun connectToDevice(device: BluetoothDevice) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController
            .connectToDevice(device)
            .listen()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _state.update { it.copy(isConnecting = false, isConnected = false) }
    }

    fun startScan() = bluetoothController.startDiscovery()

    fun stopScan() = bluetoothController.stopDiscovery()
    
    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(isConnected = true, isConnecting = false, errorMessage = null) }
                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(isConnected = false, isConnecting = false, errorMessage = result.message) }
                }
            }
        }
            .catch { _ ->
                bluetoothController.closeConnection()
                _state.update { it.copy(isConnected = false, isConnecting = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }
}