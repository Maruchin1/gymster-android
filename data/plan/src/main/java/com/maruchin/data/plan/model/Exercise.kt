package com.maruchin.data.plan.model

import java.util.UUID

data class Exercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sets: Sets,
    val repsRange: IntRange,
    val rest: String,
)