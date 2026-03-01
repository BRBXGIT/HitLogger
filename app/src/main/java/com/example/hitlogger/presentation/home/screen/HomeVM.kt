package com.example.hitlogger.presentation.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hitlogger.di.Dispatcher
import com.example.hitlogger.di.HitLoggerDispatcher
import com.example.hitlogger.domain.repos.HitRepo
import com.example.hitlogger.presentation.home.components.DateUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class HomeVM @Inject constructor(
    private val hitRepo: HitRepo,
    @param:Dispatcher(HitLoggerDispatcher.Io) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HomeState()
    )

    init {
        viewModelScope.launch(dispatcherIo) {
            hitRepo.getHits().collect { hits ->
                _state.update { it.copy(hits = hits) }
            }
        }
    }

    // --- Intents ---
    fun sendIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.OnMinusDay -> onPreviousDay()
            HomeIntent.OnPlusDay -> onNextDay()

            is HomeIntent.OnSelectDate -> onDateSelected(intent.date)
        }
    }

    // --- Date helpers ---
    private fun onNextDay() {
        _state.update { currentState ->
            val currentDate = DateUtils.parse(currentState.date)
            val nextDate = currentDate.plusDays(1)
            currentState.copy(date = DateUtils.format(nextDate))
        }
    }

    private fun onPreviousDay() {
        _state.update { currentState ->
            val currentDate = DateUtils.parse(currentState.date)
            val prevDate = currentDate.minusDays(1)
            currentState.copy(date = DateUtils.format(prevDate))
        }
    }

    private fun onDateSelected(newDate: LocalDate) {
        _state.update { it.copy(date = DateUtils.format(newDate)) }
    }
}