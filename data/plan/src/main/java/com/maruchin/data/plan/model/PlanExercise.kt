package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class PlanExercise(
    val id: ID = ID.random,
    val number: String,
    val name: String,
    val sets: Int,
    val repsRange: IntRange,
)