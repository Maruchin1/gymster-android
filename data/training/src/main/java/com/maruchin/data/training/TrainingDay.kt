package com.maruchin.data.training

import com.maruchin.core.model.ID

data class TrainingDay(
    val id: ID = ID.random(),
    val name: String,
    val exercises: List<Exercise>,
)
