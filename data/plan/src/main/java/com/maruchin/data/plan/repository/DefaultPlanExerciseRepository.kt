package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.database.dao.ExerciseDao
import com.maruchin.data.plan.database.model.asEntity
import com.maruchin.data.plan.model.PlanExercise
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPlanExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao
) : PlanExerciseRepository {

    override suspend fun create(
        dayId: ID,
        number: String,
        name: String,
        sets: Int,
        repsRange: IntRange
    ): PlanExercise {
        val exercise = PlanExercise(
            number = number,
            name = name,
            sets = sets,
            repsRange = repsRange,
        )
        val databaseExercise = exercise.asEntity(dayId.value)
        exerciseDao.insert(databaseExercise)
        return exercise
    }

    override suspend fun update(
        exerciseId: ID,
        number: String,
        name: String,
        sets: Int,
        repsRange: IntRange
    ) {
        exerciseDao.update(
            exerciseId = exerciseId.value,
            number = number,
            name = name,
            sets = sets,
            minReps = repsRange.first,
            maxReps = repsRange.last
        )
    }

    override suspend fun delete(exerciseId: ID) {
        exerciseDao.deleteById(exerciseId = exerciseId.value)
    }
}