package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.dao.SetDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultJournalSetRepository @Inject constructor(
    private val setDao: SetDao,
) : JournalSetRepository {

    override suspend fun updateWeight(setId: ID, weight: Float?) {
        setDao.updateWeight(setId.value, weight)
    }

    override suspend fun updateReps(setId: ID, reps: Int?) {
        setDao.updateReps(setId.value, reps)
    }
}