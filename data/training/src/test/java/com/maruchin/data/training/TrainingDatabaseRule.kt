package com.maruchin.data.training

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.maruchin.data.training.database.TrainingDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class TrainingDatabaseRule : TestWatcher() {
    lateinit var database: TrainingDatabase

    override fun starting(description: Description) {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TrainingDatabase::class.java,
        ).build()
    }

    override fun finished(description: Description) {
        database.close()
    }
}