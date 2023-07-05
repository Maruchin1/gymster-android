package com.maruchin.data.training.model

import com.maruchin.core.model.ID

data class Exercise(
    val id: ID = ID.random,
    val number: String,
    val name: String,
    val repsRange: IntRange,
    val sets: List<ExerciseSet>,
)
