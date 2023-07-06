package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.dao.TrainingDayDao
import com.maruchin.data.training.database.model.toExternal
import com.maruchin.data.training.model.TrainingDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultTrainingDayRepository @Inject constructor(
    private val trainingDayDao: TrainingDayDao,
) : TrainingDayRepository {

    override fun getById(trainingDayId: ID): Flow<TrainingDay?> {
        return trainingDayDao.getById(trainingDayId.value).map { it?.toExternal() }
    }
}