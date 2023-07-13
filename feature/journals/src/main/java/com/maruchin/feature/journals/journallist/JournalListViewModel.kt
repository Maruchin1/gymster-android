package com.maruchin.feature.journals.journallist

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
internal class JournalListViewModel @Inject constructor(
    private val journalRepository: JournalRepository,
) : ViewModel() {
    private val journals = journalRepository.getAll()
    private val journalSelected = MutableStateFlow(false)

    val uiState: StateFlow<JournalListUiState> = combine(
        journals,
        journalSelected,
        JournalListUiState::Success
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), JournalListUiState.Loading)

    fun selectJournal(journal: Journal) = viewModelScope.launch {
        journalRepository.setActive(journal.id)
        journalSelected.emit(true)
    }

    fun deleteJournal(journal: Journal) = viewModelScope.launch {
        journalRepository.delete(journal.id)
    }
}