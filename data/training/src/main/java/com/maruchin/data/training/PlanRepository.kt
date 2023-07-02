package com.maruchin.data.training

import com.maruchin.core.model.ID
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    fun getAll(): Flow<List<Plan>>

    fun getById(id: ID): Flow<Plan?>
}