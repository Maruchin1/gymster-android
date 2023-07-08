package com.maruchin.data.plan.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.maruchin.data.plan.PlanDatabaseRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultPlanDayRepositoryTest {

    @get:Rule
    internal val planDatabaseRule = PlanDatabaseRule()

    private lateinit var planRepository: PlanRepository
    private lateinit var planDayRepository: PlanDayRepository

    @Before
    fun before() {
        val database = planDatabaseRule.database
        planRepository = DefaultPlanRepository(
            database.planDao(),
            database.dayDao(),
            database.exerciseDao()
        )
        planDayRepository = DefaultPlanDayRepository(
            dayDao = database.dayDao()
        )
    }

    @Test
    fun `Create day`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)

        planRepository.getById(existingPlan.id).test {
            val daysBeforeCreation = awaitItem()!!.days
            assertEquals(0, daysBeforeCreation.size)

            planDayRepository.create(existingPlan.id, name = "Push")
            val createdDay = awaitItem()!!.days[0]
            assertEquals("Push", createdDay.name)
            assertEquals(0, createdDay.exercises.size)
        }
    }

    @Test
    fun `Update existing day`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)
        val existingDay = planDayRepository.create(existingPlan.id, name = "Push")

        planRepository.getById(existingPlan.id).test {
            val dayBeforeUpdate = awaitItem()!!.days[0]
            assertEquals(existingDay, dayBeforeUpdate)

            planDayRepository.update(existingDay.id, name = "Pull")
            val updatedDay = awaitItem()!!.days[0]
            assertEquals("Pull", updatedDay.name)
        }
    }

    @Test
    fun `Delete existing day`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)
        val existingDay = planDayRepository.create(existingPlan.id, name = "Push")

        planRepository.getById(existingPlan.id).test {
            val daysBeforeDelete = awaitItem()!!.days
            assertEquals(1, daysBeforeDelete.size)

            planDayRepository.delete(existingDay.id)
            val daysAfterDelete = awaitItem()!!.days
            assertEquals(0, daysAfterDelete.size)
        }
    }
}