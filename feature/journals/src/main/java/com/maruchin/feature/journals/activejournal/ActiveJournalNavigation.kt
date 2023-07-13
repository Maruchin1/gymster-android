package com.maruchin.feature.journals.activejournal

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.training.model.ExerciseProgress
import com.maruchin.data.training.model.TrainingProgress
import com.maruchin.feature.journals.ROUTE_ACTIVE_JOURNAL

internal fun NavGraphBuilder.activeLogScreen(
    onSelectLog: () -> Unit,
    onEditExercise: (TrainingProgress, ExerciseProgress) -> Unit,
) {
    composable(ROUTE_ACTIVE_JOURNAL) {
        val viewModel = hiltViewModel<ActiveJournalViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        ActiveJournalScreen(
            state = state,
            onSelectLog = onSelectLog,
            onEditExercise = onEditExercise,
        )
    }
}
