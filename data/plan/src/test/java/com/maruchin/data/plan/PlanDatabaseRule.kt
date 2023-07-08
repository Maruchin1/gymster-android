package com.maruchin.data.plan

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.maruchin.data.plan.database.PlanDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

internal class PlanDatabaseRule : TestWatcher() {
    lateinit var database: PlanDatabase

    override fun starting(description: Description) {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlanDatabase::class.java
        ).build()
    }

    override fun finished(description: Description) {
        database.close()
    }
}