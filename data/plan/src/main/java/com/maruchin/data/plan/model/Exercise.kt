package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class Exercise(
    val id: ID = ID.random,
    val name: String,
    val sets: Sets,
    val repsRange: IntRange,
    val rest: String,
)