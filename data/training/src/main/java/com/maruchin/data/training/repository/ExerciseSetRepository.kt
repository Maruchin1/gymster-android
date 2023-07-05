package com.maruchin.data.training.repository

import com.maruchin.core.model.ID

interface ExerciseSetRepository {

    suspend fun updateWeight(setId: ID, weight: Float?)

    suspend fun updateReps(setId: ID, reps: Int?)
}