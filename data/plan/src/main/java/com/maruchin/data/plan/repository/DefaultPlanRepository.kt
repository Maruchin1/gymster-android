package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPlanRepository @Inject constructor() : PlanRepository {
    private val plans = MutableStateFlow(listOf(samplePlan))

    override fun getAll(): Flow<List<Plan>> {
        return plans
    }

    override fun getById(planId: ID): Flow<Plan?> {
        return plans.map { plans ->
            plans.find { it.id == planId }
        }
    }
}