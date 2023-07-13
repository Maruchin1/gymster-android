package com.maruchin.feature.journals.activejournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.data.training.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class ActiveJournalViewModel @Inject constructor(
    journalRepository: JournalRepository,
) : ViewModel() {
    private val activeJournal = journalRepository.getActive()

    val uiState: StateFlow<ActiveJournalUiState> = activeJournal
        .map { journal ->
            if (journal != null) {
                ActiveJournalUiState.Success(journal)
            } else {
                ActiveJournalUiState.NoJournal
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, ActiveJournalUiState.Loading)
}