package com.maruchin.data.training

import com.maruchin.core.model.ID

data class ExerciseSet(
    val id: ID = ID.random(),
    val weight: Float? = null,
    val reps: Int? = null,
)
