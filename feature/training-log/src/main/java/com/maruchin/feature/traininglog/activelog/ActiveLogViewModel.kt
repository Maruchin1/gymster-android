package com.maruchin.feature.traininglog.activelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.ui.loggingCoroutineExceptionHandler
import com.maruchin.data.training.repository.TrainingLogRepository
import com.maruchin.data.training.model.TrainingWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ActiveLogViewModel @Inject constructor(
    private val trainingLogRepository: TrainingLogRepository,
) : ViewModel() {
    private val exceptionHandler = loggingCoroutineExceptionHandler()
    private val activeLog = trainingLogRepository.getActive()

    val uiState: StateFlow<ActiveLogUiState> = activeLog
        .map(::ActiveLogUiState)
        .stateIn(viewModelScope, SharingStarted.Lazily, ActiveLogUiState())

    fun changeCurrentWeek(week: TrainingWeek) = viewModelScope.launch(exceptionHandler) {
        val log = trainingLogRepository.getActive().first().let(::checkNotNull)
        trainingLogRepository.changeCurrentWeek(log.id, week.id)
    }
}