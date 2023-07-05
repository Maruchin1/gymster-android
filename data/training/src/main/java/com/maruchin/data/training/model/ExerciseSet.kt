package com.maruchin.data.training.model

import com.maruchin.core.model.ID

data class ExerciseSet(
    val id: ID = ID.random,
    val weight: Float? = null,
    val reps: Int? = null,
)
