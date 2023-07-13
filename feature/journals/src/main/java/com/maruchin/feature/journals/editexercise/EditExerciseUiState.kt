package com.maruchin.feature.journals.editexercise

import com.maruchin.data.training.model.ExerciseProgress
import com.maruchin.data.training.model.TrainingProgress

internal sealed interface EditExerciseUiState {

    object Loading : EditExerciseUiState

    data class Success(
        val training: TrainingProgress,
        val initialExercise: ExerciseProgress,
    ) : EditExerciseUiState
}

internal fun EditExerciseUiState.Success.getInitialExerciseIndex(): Int {
    return training.exercisesProgress.indexOf(initialExercise)
}
