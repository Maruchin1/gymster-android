package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    fun getAll(): Flow<List<Plan>>

    fun getById(planId: ID): Flow<Plan?>

    suspend fun create(name: String, weeks: Int): Plan

    suspend fun update(planId: ID, name: String, weeks: Int)

    suspend fun delete(planId: ID)
}