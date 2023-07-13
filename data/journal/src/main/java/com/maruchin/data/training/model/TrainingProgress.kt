package com.maruchin.data.training.model

import com.maruchin.data.plan.model.Training
import java.util.UUID

data class TrainingProgress(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val exercisesProgress: List<ExerciseProgress>,
) {

    constructor(training: Training) : this(
        name = training.name,
        exercisesProgress = training.exercises.map { ExerciseProgress(it) }
    )
}
