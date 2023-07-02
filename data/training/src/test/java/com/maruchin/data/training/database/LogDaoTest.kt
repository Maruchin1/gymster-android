package com.maruchin.data.training.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.training.Log
import com.maruchin.data.training.samplePlan
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogDaoTest {
    private lateinit var trainingDatabase: TrainingDatabase
    private lateinit var logDao: LogDao

    @Before
    fun createDatabase() {
        trainingDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TrainingDatabase::class.java
        ).build()
        logDao = trainingDatabase.logDao()
    }

    @After
    fun closeDatabase() {
        trainingDatabase.close()
    }

    @Test
    fun `Get all logs when database is empty`() = runTest {
        logDao.getAll().test {
            with(awaitItem()) {
                assertTrue(isEmpty())
            }
        }
    }

    @Test
    fun `Insert new log`() = runTest {
        logDao.getAll().test {
            with(awaitItem()) {
                assertTrue(isEmpty())
            }

            val log = Log(name = "Mój pierwszy trening", plan = samplePlan)
            logDao.insert(log.toDatabase())
            with(awaitItem()) {
                assertEquals(1, size)
                assertEquals(log.name, first().log.name)
            }
        }
    }
}