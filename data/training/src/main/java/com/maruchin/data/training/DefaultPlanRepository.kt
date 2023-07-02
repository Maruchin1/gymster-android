package com.maruchin.data.training

import com.maruchin.core.model.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPlanRepository @Inject constructor() : PlanRepository {

    override fun getAll(): Flow<List<Plan>> {
        return flowOf(listOf(samplePlan))
    }

    override fun getById(id: ID): Flow<Plan?> {
        return flowOf(samplePlan)
    }
}