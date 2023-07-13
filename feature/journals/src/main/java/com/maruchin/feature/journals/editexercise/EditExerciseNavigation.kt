package com.maruchin.feature.journals.editexercise

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.feature.journals.ARG_EXERCISE_PROGRESS_ID
import com.maruchin.feature.journals.ARG_TRAINING_PROGRESS_ID
import com.maruchin.feature.journals.ROUTE_EDIT_EXERCISE

internal fun NavGraphBuilder.editExerciseScreen(onBack: () -> Unit) {
    composable("$ROUTE_EDIT_EXERCISE/{$ARG_TRAINING_PROGRESS_ID}/{$ARG_EXERCISE_PROGRESS_ID}") {
        val viewModel = hiltViewModel<EditExerciseViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        EditExerciseScreen(
            state = state,
            onBack = onBack,
            onChangeWeight = viewModel::changeWeight,
            onChangeReps = viewModel::changeReps,
        )
    }
}

internal fun NavController.navigateToEditExercise(
    trainingProgressId: String,
    exerciseProgressId: String,
) {
    navigate("$ROUTE_EDIT_EXERCISE/$trainingProgressId/$exerciseProgressId")
}
