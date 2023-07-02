package com.maruchin.feature.traininglog.activelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class ActiveLogViewModel @Inject constructor(
    logRepository: LogRepository,
) : ViewModel() {
    private val activeLog = logRepository.getActive().filterNotNull()

    val uiState: StateFlow<ActiveLogUiState> = activeLog
        .map(ActiveLogUiState::Success)
        .stateIn(viewModelScope, SharingStarted.Lazily, ActiveLogUiState.Loading)
}