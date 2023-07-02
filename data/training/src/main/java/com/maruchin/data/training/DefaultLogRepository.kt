package com.maruchin.data.training

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.LogDao
import com.maruchin.data.training.database.toDatabase
import com.maruchin.data.training.database.toExternal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultLogRepository @Inject constructor(
    private val logDao: LogDao,
) : LogRepository {

    override fun getAll(): Flow<List<Log>> {
        return logDao.getAll().map { it.toExternal() }
    }

    override fun getActive(): Flow<Log?> {
        return logDao.getActive().map { it?.toExternal() }
    }

    override suspend fun createNew(logName: String, plan: Plan) {
        Log(logName, plan).toDatabase().let { newLog ->
            logDao.insert(newLog)
        }
    }

    override suspend fun activate(logId: ID) {
        logDao.activate(logId.value)
    }

    override suspend fun delete(logId: ID) {
        logDao.delete(logId.value)
    }
}
