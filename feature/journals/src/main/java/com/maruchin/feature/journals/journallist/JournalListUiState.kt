package com.maruchin.feature.journals.journallist

import com.maruchin.data.training.model.Journal

internal sealed interface JournalListUiState {

    object Loading : JournalListUiState

    data class Success(
        val journals: List<Journal>,
        val journalSelected: Boolean,
    ) : JournalListUiState
}