package com.maruchin.data.plan.repository

import com.maruchin.data.plan.model.Plan
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    fun getAll(): Flow<List<Plan>>

    fun getById(planId: String): Flow<Plan?>
}