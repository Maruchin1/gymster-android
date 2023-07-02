package com.maruchin.data.training

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.ExerciseSetDao
import com.maruchin.data.training.database.toExternal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultExerciseSetRepository @Inject constructor(
    private val exerciseSetDao: ExerciseSetDao,
) : ExerciseSetRepository {

    override fun getById(setId: ID): Flow<ExerciseSet?> {
        return exerciseSetDao.getById(setId.value).map { it?.toExternal() }
    }

    override suspend fun update(setId: ID, weight: Float?, reps: Int?) {
        exerciseSetDao.update(setId.value, weight, reps)
    }
}