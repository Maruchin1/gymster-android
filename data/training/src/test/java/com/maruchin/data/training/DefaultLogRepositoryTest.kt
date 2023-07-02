package com.maruchin.data.training

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.training.database.TrainingDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DefaultLogRepositoryTest {
    private lateinit var trainingDatabase: TrainingDatabase
    private lateinit var logRepository: LogRepository

    @Before
    fun before() {
        trainingDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TrainingDatabase::class.java
        ).build()
        logRepository = DefaultLogRepository(trainingDatabase.logDao())
    }

    @After
    fun after() {
        trainingDatabase.close()
    }

    @Test
    fun `Get all logs when database empty`() = runTest {
        logRepository.getAll().test {
            assertEquals(emptyList<Log>(), awaitItem())
        }
    }

    @Test
    fun `Create new log and get all logs`() = runTest {
        logRepository.getAll().test {
            assertEquals(emptyList<Log>(), awaitItem())

            logRepository.createNew("My first log", samplePlan)

            with(awaitItem()) {
                assertEquals(1, size)
                assertEquals("My first log", first().name)
            }
        }
    }
}