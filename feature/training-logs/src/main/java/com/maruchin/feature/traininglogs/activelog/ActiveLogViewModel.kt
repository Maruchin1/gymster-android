package com.maruchin.feature.traininglogs.activelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.core.ui.loggingCoroutineExceptionHandler
import com.maruchin.data.training.repository.JournalRepository
import com.maruchin.data.training.model.JournalWeek
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
    private val journalRepository: JournalRepository,
) : ViewModel() {
    private val exceptionHandler = loggingCoroutineExceptionHandler()
    private val activeLog = journalRepository.getActive()

    val uiState: StateFlow<ActiveLogUiState> = activeLog
        .map(::ActiveLogUiState)
        .stateIn(viewModelScope, SharingStarted.Lazily, ActiveLogUiState())

    fun changeCurrentWeek(week: JournalWeek) = viewModelScope.launch(exceptionHandler) {
        val log = journalRepository.getActive().first().let(::checkNotNull)
        journalRepository.changeCurrentWeek(log.id, week.id)
    }
}