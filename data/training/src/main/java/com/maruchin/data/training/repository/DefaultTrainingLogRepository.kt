package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.dao.TrainingLogDao
import com.maruchin.data.training.database.model.toDatabase
import com.maruchin.data.training.database.model.toExternal
import com.maruchin.data.training.model.TrainingLog
import com.maruchin.data.training.model.Plan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultTrainingLogRepository @Inject constructor(
    private val trainingLogDao: TrainingLogDao,
) : TrainingLogRepository {

    override fun getAll(): Flow<List<TrainingLog>> {
        return trainingLogDao.getAll().map { it.toExternal() }
    }

    override fun getActive(): Flow<TrainingLog?> {
        return trainingLogDao.getActive().map { it?.toExternal() }
    }

    override suspend fun createNew(logName: String, plan: Plan) {
        TrainingLog(logName, plan).toDatabase().let { newLog ->
            trainingLogDao.insert(newLog)
        }
    }

    override suspend fun activate(logId: ID) {
        trainingLogDao.activate(logId.value)
    }

    override suspend fun changeCurrentWeek(logId: ID, weekID: ID) {
        trainingLogDao.changeActiveWeek(logId.value, weekID.value)
    }

    override suspend fun delete(logId: ID) {
        trainingLogDao.delete(logId.value)
    }
}
