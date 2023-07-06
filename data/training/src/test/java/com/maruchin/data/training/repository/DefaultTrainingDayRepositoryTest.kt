package com.maruchin.data.training.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.training.TrainingDatabaseRule
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
class DefaultTrainingDayRepositoryTest {

    @get:Rule
    internal val trainingDatabaseRule = TrainingDatabaseRule()

    private lateinit var trainingLogDao: TrainingLogDao
    private lateinit var trainingDayRepository: TrainingDayRepository

    @Before
    fun setup() {
        val database = trainingDatabaseRule.database
        trainingLogDao = database.trainingLogDao()
        trainingDayRepository = DefaultTrainingDayRepository(database.trainingDayDao())
    }

    @Test
    fun `Get training day by id when database is empty`() = runTest {
        val trainingDay = sampleEmptyTrainingLog.weeks[1].days[2]

        trainingDayRepository.getById(trainingDay.id).test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `Get training day by id when it as added`() = runTest {
        val trainingDay = sampleEmptyTrainingLog.weeks[1].days[2]

        trainingDayRepository.getById(trainingDay.id).test {
            assertNull(awaitItem())

            trainingLogDao.insert(sampleEmptyTrainingLog.toDatabase())
            assertEquals(trainingDay, awaitItem())
        }
    }
}