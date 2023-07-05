package com.maruchin.feature.traininglog.selectlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.model.TrainingLog
import com.maruchin.data.training.repository.TrainingLogRepository
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
    private val trainingLogRepository: TrainingLogRepository,
) : ViewModel() {
    private val allLogs = trainingLogRepository.getAll()
    private val logSelected = MutableStateFlow(false)

    val uiState: StateFlow<SelectLogUiState> = combine(
        allLogs,
        logSelected,
        ::SelectLogUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SelectLogUiState())

    fun selectLog(trainingLog: TrainingLog) = viewModelScope.launch {
        trainingLogRepository.activate(trainingLog.id)
        logSelected.value = true
    }

    fun deleteLog(trainingLog: TrainingLog) = viewModelScope.launch {
        trainingLogRepository.delete(trainingLog.id)
    }
}