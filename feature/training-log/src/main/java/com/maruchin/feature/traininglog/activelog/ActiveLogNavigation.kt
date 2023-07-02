package com.maruchin.feature.traininglog.activelog

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maruchin.core.model.ID
import com.maruchin.data.training.ExerciseSet

internal const val ACTIVE_LOG_ROUTE = "active-log"

internal fun NavGraphBuilder.activeLogScreen(
    onSelectLog: () -> Unit,
    onEditExerciseSet: (ExerciseSet) -> Unit,
) {
    composable(ACTIVE_LOG_ROUTE) {
        val viewModel = hiltViewModel<ActiveLogViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        ActiveLogScreen(
            state = state,
            onSelectLog = onSelectLog,
            onEditExerciseSet = onEditExerciseSet,
        )
    }
}

internal fun NavController.navigateToActiveLog(options: NavOptions? = null) {
    navigate(ACTIVE_LOG_ROUTE, options)
}