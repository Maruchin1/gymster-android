package com.maruchin.data.training.repository

import com.maruchin.data.training.database.dao.TrainingProgressDao
import com.maruchin.data.training.database.model.asDomain
import com.maruchin.data.training.model.TrainingProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultTrainingProgressRepository @Inject constructor(
    private val trainingProgressDao: TrainingProgressDao,
) : TrainingProgressRepository {

    override fun getById(dayId: String): Flow<TrainingProgress?> {
        return trainingProgressDao.getById(dayId).map { it?.asDomain() }
    }
}