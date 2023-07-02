package com.maruchin.data.training

import com.maruchin.core.model.ID
import kotlinx.coroutines.flow.Flow

interface LogRepository {

    fun getAll(): Flow<List<Log>>

    fun getActive(): Flow<Log?>

    suspend fun createNew(logName: String, plan: Plan)

    suspend fun activate(logId: ID)

    suspend fun delete(logId: ID)
}