package com.maruchin.feature.journals.createjournal

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.journals.ROUTE_CREATE_JOURNAL

internal fun NavGraphBuilder.createJournalScreen(onBack: () -> Unit) {
    composable(ROUTE_CREATE_JOURNAL) {
        val viewModel = hiltViewModel<CreateJournalViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(state.journalSaved) {
            if (state.journalSaved) onBack()
        }
        CreateJournalScreen(
            state = state,
            onClose = onBack,
            onSave = viewModel::save,
            onChangeName = viewModel::changeName,
            onSelectPlan = viewModel::selectPlan,
        )
    }
}

internal fun NavController.navigateToCreateJournal() {
    navigate(ROUTE_CREATE_JOURNAL)
}