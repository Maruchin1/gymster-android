package com.maruchin.feature.journals.journallist

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.journals.ROUTE_JOURNAL_LIST

internal fun NavGraphBuilder.journalListScreen(onBack: () -> Unit, onCreateJournal: () -> Unit) {
    composable(ROUTE_JOURNAL_LIST) {
        val viewModel: JournalListViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val journalSelected = (state as? JournalListUiState.Success)?.journalSelected ?: false
        LaunchedEffect(journalSelected) {
            if (journalSelected) onBack()
        }
        JournalListScreen(
            state = state,
            onBack = onBack,
            onSelectJournal = viewModel::selectJournal,
            onDeleteJournal = viewModel::deleteJournal,
            onCreateJournal = onCreateJournal
        )
    }
}

internal fun NavController.navigateToJournalList() {
    navigate(ROUTE_JOURNAL_LIST)
}