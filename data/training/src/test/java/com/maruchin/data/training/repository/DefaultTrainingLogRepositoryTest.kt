package com.maruchin.data.training.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.training.TrainingDatabaseRule
import com.maruchin.data.training.model.TrainingLog
import com.maruchin.data.training.model.sampleEmptyTrainingLog
import com.maruchin.data.training.model.sampleTrainingPlan
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultTrainingLogRepositoryTest {

    @get:Rule
    internal val trainingDatabaseRule = TrainingDatabaseRule()

    private lateinit var trainingLogRepository: TrainingLogRepository

    @Before
    fun setup() {
        trainingLogRepository = DefaultTrainingLogRepository(
            trainingDatabaseRule.database.trainingLogDao()
        )
    }

    @Test
    fun `Get all training logs when database is empty`() = runTest {
        trainingLogRepository.getAll().test {
            val logs = awaitItem()
            assertTrue(logs.isEmpty())
        }
    }

    @Test
    fun `Get active training log when database is empty`() = runTest {
        trainingLogRepository.getActive().test {
            val logs = awaitItem()
            assertNull(logs)
        }
    }

    @Test
    fun `Create new training log`() = runTest {
        trainingLogRepository.getAll().test {
            assertEquals(emptyList<TrainingLog>(), awaitItem())

            trainingLogRepository.createNew(sampleEmptyTrainingLog.name, sampleTrainingPlan)
            val logs = awaitItem()
            assertEquals(1, logs.size)
        }
    }

    @Test
    fun `Activate training log`() = runTest {
        val trainingLog = trainingLogRepository.createNew(
            sampleEmptyTrainingLog.name,
            sampleTrainingPlan
        )

        trainingLogRepository.getActive().test {
            assertNull(awaitItem())

            trainingLogRepository.activate(trainingLog.id)
            val activeTrainingLog = awaitItem()!!
            assertTrue(activeTrainingLog.active)
        }
    }

    @Test
    fun `Change current week`() = runTest {
        val trainingLog = trainingLogRepository.createNew(
            sampleEmptyTrainingLog.name,
            sampleTrainingPlan,
        )
        trainingLogRepository.activate(trainingLog.id)

        trainingLogRepository.getActive().test {
            val firstWeek = trainingLog.weeks[0]
            val firstWeekTrainingLog = awaitItem()!!
            assertEquals(firstWeek.id, firstWeekTrainingLog.currentWeekId)

            val thirdWeek = trainingLog.weeks[2]
            trainingLogRepository.changeCurrentWeek(trainingLog.id, thirdWeek.id)
            val thirdWeekTrainingLog = awaitItem()!!
            assertEquals(thirdWeek.id, thirdWeekTrainingLog.currentWeekId)
        }
    }

    @Test
    fun `Delete training log`() = runTest {
        val trainingLog = trainingLogRepository.createNew(
            sampleEmptyTrainingLog.name,
            sampleTrainingPlan
        )

        trainingLogRepository.getAll().test {
            val logsBeforeDelete = awaitItem()
            assertEquals(1, logsBeforeDelete.size)

            trainingLogRepository.delete(trainingLog.id)
            val logsAfterDelete = awaitItem()
            assertEquals(0, logsAfterDelete.size)
        }
    }
}