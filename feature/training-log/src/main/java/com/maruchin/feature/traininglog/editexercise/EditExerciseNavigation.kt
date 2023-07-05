package com.maruchin.feature.traininglog.editexercise

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maruchin.core.model.ID

internal const val EDIT_EXERCISE_ROUTE = "edit-exercise"
internal const val TRAINING_DAY_ID_ARG = "trainingDayId"
internal const val EXERCISE_ID_ARG = "exerciseId"

internal fun NavGraphBuilder.editExerciseScreen(onBack: () -> Unit) {
    composable("$EDIT_EXERCISE_ROUTE/{$TRAINING_DAY_ID_ARG}/{$EXERCISE_ID_ARG}") {
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
    trainingDayId: ID,
    exerciseId: ID,
    options: NavOptions? = null
) {
    navigate("$EDIT_EXERCISE_ROUTE/${trainingDayId.value}/${exerciseId.value}", options)
}

internal val SavedStateHandle.trainingDayId: ID
    get() = ID(requireNotNull(get(TRAINING_DAY_ID_ARG)))

internal val SavedStateHandle.exerciseId: ID
    get() = ID(requireNotNull(get(EXERCISE_ID_ARG)))
