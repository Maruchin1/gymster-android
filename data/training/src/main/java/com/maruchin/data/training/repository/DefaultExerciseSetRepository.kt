package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.dao.ExerciseSetDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultExerciseSetRepository @Inject constructor(
    private val exerciseSetDao: ExerciseSetDao,
) : ExerciseSetRepository {

    override suspend fun updateWeight(setId: ID, weight: Float?) {
        exerciseSetDao.updateWeight(setId.value, weight)
    }

    override suspend fun updateReps(setId: ID, reps: Int?) {
        exerciseSetDao.updateReps(setId.value, reps)
    }
}