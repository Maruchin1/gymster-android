package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingPlan
import com.maruchin.data.training.model.sampleTrainingPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultTrainingPlanRepository @Inject constructor() : TrainingPlanRepository {

    override fun getAll(): Flow<List<TrainingPlan>> {
        return flowOf(listOf(sampleTrainingPlan))
    }

    override fun getById(id: ID): Flow<TrainingPlan?> {
        return flowOf(sampleTrainingPlan)
    }
}