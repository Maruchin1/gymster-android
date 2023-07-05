package com.maruchin.feature.traininglogs.activelog

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.data.training.model.Exercise
import com.maruchin.data.training.model.TrainingDay

internal const val ACTIVE_LOG_ROUTE = "active-log"

internal fun NavGraphBuilder.activeLogScreen(
    onSelectLog: () -> Unit,
    onEditExercise: (TrainingDay, Exercise) -> Unit,
) {
    composable(ACTIVE_LOG_ROUTE) {
        val viewModel = hiltViewModel<ActiveLogViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        ActiveLogScreen(
            state = state,
            onSelectLog = onSelectLog,
            onChangeCurrentWeek = viewModel::changeCurrentWeek,
            onEditExercise = onEditExercise,
        )
    }
}
