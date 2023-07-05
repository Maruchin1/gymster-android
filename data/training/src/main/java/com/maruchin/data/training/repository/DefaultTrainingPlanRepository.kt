package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Plan
import com.maruchin.data.training.model.samplePlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultTrainingPlanRepository @Inject constructor() : TrainingPlanRepository {

    override fun getAll(): Flow<List<Plan>> {
        return flowOf(listOf(samplePlan))
    }

    override fun getById(id: ID): Flow<Plan?> {
        return flowOf(samplePlan)
    }
}