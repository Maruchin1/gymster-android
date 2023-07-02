package com.maruchin.feature.traininglog.selectlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.Log
import com.maruchin.data.training.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SelectLogViewModel @Inject constructor(
    private val logRepository: LogRepository,
) : ViewModel() {
    private val allLogs = logRepository.getAll()
    private val logSelected = MutableStateFlow(false)

    val uiState: StateFlow<SelectLogUiState> = combine(
        allLogs,
        logSelected,
        ::SelectLogUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SelectLogUiState())

    fun selectLog(log: Log) = viewModelScope.launch {
        logRepository.activate(log.id)
        logSelected.value = true
    }

    fun deleteLog(log: Log) = viewModelScope.launch {
        logRepository.delete(log.id)
    }
}