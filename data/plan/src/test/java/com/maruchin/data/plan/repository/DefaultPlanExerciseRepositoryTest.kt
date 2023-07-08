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
class DefaultPlanExerciseRepositoryTest {

    @get:Rule
    internal val planDatabaseRule = PlanDatabaseRule()

    private lateinit var planRepository: PlanRepository
    private lateinit var planDayRepository: PlanDayRepository
    private lateinit var planExerciseRepository: PlanExerciseRepository

    @Before
    fun before() {
        val database = planDatabaseRule.database
        planRepository = DefaultPlanRepository(
            database.planDao(),
            database.dayDao(),
            database.exerciseDao(),
        )
        planDayRepository = DefaultPlanDayRepository(
            database.dayDao()
        )
        planExerciseRepository = DefaultPlanExerciseRepository(
            database.exerciseDao()
        )
    }

    @Test
    fun `Create exercise`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)
        val existingDay = planDayRepository.create(existingPlan.id, name = "Push")

        planRepository.getById(existingPlan.id).test {
            val exercisesBeforeCreating = awaitItem()!!.days[0].exercises
            assertEquals(0, exercisesBeforeCreating.size)

            planExerciseRepository.create(
                dayId = existingDay.id,
                number = "1",
                name = "Wyciskanie sztangi na ławce poziomej",
                sets = 3,
                repsRange = 8..10
            )
            val createdExercise =  awaitItem()!!.days[0].exercises[0]
            assertEquals("1", createdExercise.number)
            assertEquals("Wyciskanie sztangi na ławce poziomej", createdExercise.name)
            assertEquals(3, createdExercise.sets)
            assertEquals(8..10, createdExercise.repsRange)
        }
    }

    @Test
    fun `Update exercise`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)
        val existingDay = planDayRepository.create(existingPlan.id, name = "Push")
        val existingExercise = planExerciseRepository.create(
            dayId = existingDay.id,
            number = "1",
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = 3,
            repsRange = 8..10
        )

        planRepository.getById(existingPlan.id).test {
            val exerciseBeforeUpdate = awaitItem()!!.days[0].exercises[0]
            assertEquals(existingExercise, exerciseBeforeUpdate)

            planExerciseRepository.update(
                exerciseId = existingExercise.id,
                number = "2",
                name = "Wyciskanie hantli w skosie dodatnim",
                sets = 4,
                repsRange = 10..12
            )
            val updatedExercise = awaitItem()!!.days[0].exercises[0]
            assertEquals("2", updatedExercise.number)
            assertEquals("Wyciskanie hantli w skosie dodatnim", updatedExercise.name)
            assertEquals(4, updatedExercise.sets)
            assertEquals(10..12, updatedExercise.repsRange)
        }
    }

    @Test
    fun `Delete exercise`() = runTest {
        val existingPlan = planRepository.create(name = "Push Pull", weeks = 12)
        val existingDay = planDayRepository.create(existingPlan.id, name = "Push")
        val existingExercise = planExerciseRepository.create(
            dayId = existingDay.id,
            number = "1",
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = 3,
            repsRange = 8..10
        )

        planRepository.getById(existingPlan.id).test {
            val exercisesBeforeDelete = awaitItem()!!.days[0].exercises
            assertEquals(1, exercisesBeforeDelete.size)

            planExerciseRepository.delete(existingExercise.id)
            val exercisesAfterDelete = awaitItem()!!.days[0].exercises
            assertEquals(0, exercisesAfterDelete.size)
        }
    }
}