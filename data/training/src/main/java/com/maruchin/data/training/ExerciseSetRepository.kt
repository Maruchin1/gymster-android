package com.maruchin.data.training

import com.maruchin.core.model.ID
import kotlinx.coroutines.flow.Flow

interface ExerciseSetRepository {

    fun getById(setId: ID): Flow<ExerciseSet?>

    suspend fun update(setId: ID, weight: Float?, reps: Int?)
}