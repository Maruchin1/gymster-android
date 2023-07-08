package com.maruchin.feature.traininglogs.selectlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.model.Journal
import com.maruchin.data.training.repository.JournalRepository
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
    private val journalRepository: JournalRepository,
) : ViewModel() {
    private val allLogs = journalRepository.getAll()
    private val logSelected = MutableStateFlow(false)

    val uiState: StateFlow<SelectLogUiState> = combine(
        allLogs,
        logSelected,
        ::SelectLogUiState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SelectLogUiState())

    fun selectLog(journal: Journal) = viewModelScope.launch {
        journalRepository.activate(journal.id)
        logSelected.value = true
    }

    fun deleteLog(journal: Journal) = viewModelScope.launch {
        journalRepository.delete(journal.id)
    }
}