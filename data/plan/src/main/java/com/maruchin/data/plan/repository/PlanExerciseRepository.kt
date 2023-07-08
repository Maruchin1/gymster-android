package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanExercise

interface PlanExerciseRepository {

    suspend fun create(
        dayId: ID,
        number: String,
        name: String,
        sets: Int,
        repsRange: IntRange
    ): PlanExercise

    suspend fun update(
        exerciseId: ID,
        number: String,
        name: String,
        sets: Int,
        repsRange: IntRange,
    )

    suspend fun delete(exerciseId: ID)
}