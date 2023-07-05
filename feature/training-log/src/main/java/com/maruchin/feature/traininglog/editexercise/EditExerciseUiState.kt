package com.maruchin.feature.traininglog.editexercise

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Exercise
import com.maruchin.data.training.model.TrainingDay

internal data class EditExerciseUiState(
    val name: String = "",
    val exercises: List<Exercise> = emptyList(),
    val selectedExerciseId: ID = ID.empty,
    val loading: Boolean = true,
) {

    val selectedExerciseIndex: Int
        get() = exercises.indexOfFirst { it.id == selectedExerciseId }

    constructor(trainingDay: TrainingDay?, selectedExerciseId: ID) : this(
        name = trainingDay?.name.orEmpty(),
        exercises = trainingDay?.exercises.orEmpty(),
        selectedExerciseId = selectedExerciseId,
        loading = false,
    )
}
