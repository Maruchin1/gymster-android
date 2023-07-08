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
class DefaultPlanRepositoryTest {

    @get:Rule
    internal val planDatabaseRule = PlanDatabaseRule()

    private lateinit var planRepository: PlanRepository

    @Before
    fun before() {
        val database = planDatabaseRule.database
        planRepository = DefaultPlanRepository(
            database.planDao(),
            database.dayDao(),
            database.exerciseDao()
        )
    }

    @Test
    fun `Create plan`() = runTest {
        planRepository.getAll().test {
            val plansBeforeCreation = awaitItem()
            assertEquals(0, plansBeforeCreation.size)

            planRepository.create(name = "Push Pull", weeks = 12)
            val createdPlan = awaitItem()[0]
            assertEquals("Push Pull", createdPlan.name)
            assertEquals(12, createdPlan.weeks)
            assertTrue(createdPlan.days.isEmpty())
        }
    }

    @Test
    fun `Update plan`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)

        planRepository.getById(existingPlan.id).test {
            val planBeforeUpdate = awaitItem()!!
            assertEquals(existingPlan, planBeforeUpdate)

            planRepository.update(
                planId = existingPlan.id,
                name = "Full Body",
                weeks = 8
            )
            val updatedPlan = awaitItem()!!
            assertEquals(existingPlan.id, updatedPlan.id)
            assertEquals("Full Body", updatedPlan.name)
            assertEquals(8, updatedPlan.weeks)
        }
    }

    @Test
    fun `Delete plan`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)

        planRepository.getById(existingPlan.id).test {
            assertEquals(existingPlan, awaitItem())

            planRepository.delete(existingPlan.id)
            assertNull(awaitItem())
        }
    }
}