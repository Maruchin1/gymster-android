package com.maruchin.feature.journals.activejournal

import com.maruchin.data.training.model.Journal

internal sealed interface ActiveJournalUiState {

    object Loading : ActiveJournalUiState

    object NoJournal : ActiveJournalUiState

    data class Success(val journal: Journal) : ActiveJournalUiState
}
