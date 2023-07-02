package com.maruchin.data.training

import com.maruchin.core.model.ID

data class Exercise(
    val id: ID = ID.random(),
    val number: String,
    val name: String,
    val repsRange: IntRange,
    val exerciseSets: List<ExerciseSet>,
)
