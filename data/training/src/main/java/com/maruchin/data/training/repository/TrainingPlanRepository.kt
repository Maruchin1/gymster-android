package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Plan
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {

    fun getAll(): Flow<List<Plan>>

    fun getById(id: ID): Flow<Plan?>
}