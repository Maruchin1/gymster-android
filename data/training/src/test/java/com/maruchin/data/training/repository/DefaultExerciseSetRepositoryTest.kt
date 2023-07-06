package com.maruchin.data.training.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.training.TrainingDatabaseRule
import com.maruchin.data.training.database.dao.TrainingDayDao
import com.maruchin.data.training.database.dao.TrainingLogDao
import com.maruchin.data.training.database.model.toDatabase
import com.maruchin.data.training.model.sampleEmptyTrainingLog
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultExerciseSetRepositoryTest {

    @get:Rule
    internal val trainingDatabaseRule = TrainingDatabaseRule()

    private lateinit var trainingLogDao: TrainingLogDao
    private lateinit var trainingDayDao: TrainingDayDao
    private lateinit var exerciseSetRepository: ExerciseSetRepository

    @Before
    fun setup() {
        val database = trainingDatabaseRule.database
        trainingLogDao = database.trainingLogDao()
        trainingDayDao = database.trainingDayDao()
        exerciseSetRepository = DefaultExerciseSetRepository(database.exerciseSetDao())
    }

    @Test
    fun `Update exercise set weight`() = runTest {
        trainingLogDao.insert(sampleEmptyTrainingLog.toDatabase())
        val trainingDay = sampleEmptyTrainingLog.weeks[1].days[2]
        val exerciseSet = trainingDay.exercises[1].sets[1]

        trainingDayDao.getById(trainingDay.id.value).test {
            val emptyExerciseSet = awaitItem()!!.exercises[1].sets[1]
            assertNull(emptyExerciseSet.weight)
            assertNull(emptyExerciseSet.reps)

            exerciseSetRepository.updateWeight(exerciseSet.id, 60.5f)
            val updatedExerciseSet = awaitItem()!!.exercises[1].sets[1]
            assertEquals(60.5f, updatedExerciseSet.weight)
            assertNull(updatedExerciseSet.reps)
        }
    }

    @Test
    fun `Update exercise set reps`() = runTest {
        trainingLogDao.insert(sampleEmptyTrainingLog.toDatabase())
        val trainingDay = sampleEmptyTrainingLog.weeks[1].days[2]
        val exerciseSet = trainingDay.exercises[1].sets[1]

        trainingDayDao.getById(trainingDay.id.value).test {
            val emptyExerciseSet = awaitItem()!!.exercises[1].sets[1]
            assertNull(emptyExerciseSet.weight)
            assertNull(emptyExerciseSet.reps)

            exerciseSetRepository.updateReps(exerciseSet.id, 11)
            val updatedExerciseSet = awaitItem()!!.exercises[1].sets[1]
            assertNull(updatedExerciseSet.weight)
            assertEquals(11, updatedExerciseSet.reps)
        }
    }
}