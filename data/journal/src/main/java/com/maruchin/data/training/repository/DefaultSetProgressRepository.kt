package com.maruchin.data.training.repository

import com.maruchin.data.training.database.dao.SetProgressDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultSetProgressRepository @Inject constructor(
    private val setProgressDao: SetProgressDao,
) : SetProgressRepository {

    override suspend fun updateWeight(setId: String, weight: Float?) {
        setProgressDao.updateWeight(setId, weight)
    }

    override suspend fun updateReps(setId: String, reps: Int?) {
        setProgressDao.updateReps(setId, reps)
    }
}