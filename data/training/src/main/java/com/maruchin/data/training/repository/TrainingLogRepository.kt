package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingLog
import com.maruchin.data.training.model.Plan
import kotlinx.coroutines.flow.Flow

interface TrainingLogRepository {

    fun getAll(): Flow<List<TrainingLog>>

    fun getActive(): Flow<TrainingLog?>

    suspend fun createNew(logName: String, plan: Plan)

    suspend fun activate(logId: ID)

    suspend fun changeCurrentWeek(logId: ID, weekID: ID)

    suspend fun delete(logId: ID)
}