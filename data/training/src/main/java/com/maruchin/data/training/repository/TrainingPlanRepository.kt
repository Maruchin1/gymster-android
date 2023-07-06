package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {

    fun getAll(): Flow<List<TrainingPlan>>

    fun getById(id: ID): Flow<TrainingPlan?>
}