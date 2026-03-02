package com.example.hitlogger.presentation.home.screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hitlogger.domain.repos.bluetooth.BluetoothController
import com.example.hitlogger.domain.repos.hit.HitRepo
import com.example.hitlogger.presentation.home.components.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@Stable
class HomeVM @Inject constructor(
    hitRepo: HitRepo,
    private val bluetoothController: BluetoothController
): ViewModel() {

    private val dateUtils = DateUtils()

    private val _allHits = hitRepo.getHits()
    private val _selectedDate = MutableStateFlow(LocalDate.now())

    val state = combine(
        flow = _allHits,
        flow2 = _selectedDate
    ) { hits, date ->
        val dateString = dateUtils.format(date)

        val filteredHits = hits.filter { hit ->
            hit.date.startsWith(dateString)
        }

        HomeState(
            hits = filteredHits,
            date = dateString
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HomeState()
    )

    // --- Intents ---
    fun sendIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.OnMinusDay -> _selectedDate.update { it.minusDays(1) }
            HomeIntent.OnPlusDay -> _selectedDate.update { it.plusDays(1) }
            is HomeIntent.OnSelectDate -> _selectedDate.value = intent.date
        }
    }
}