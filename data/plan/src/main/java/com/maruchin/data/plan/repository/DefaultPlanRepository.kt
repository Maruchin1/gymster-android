package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.database.dao.DayDao
import com.maruchin.data.plan.database.dao.ExerciseDao
import com.maruchin.data.plan.database.dao.PlanDao
import com.maruchin.data.plan.database.model.asEntity
import com.maruchin.data.plan.database.model.asDomain
import com.maruchin.data.plan.database.model.flatten
import com.maruchin.data.plan.model.Plan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPlanRepository @Inject constructor(
    private val planDao: PlanDao,
    private val dayDao: DayDao,
    private val exerciseDao: ExerciseDao,
) : PlanRepository {

    override fun getAll(): Flow<List<Plan>> {
        return planDao.selectAll().map { it.asDomain() }
    }

    override fun getById(planId: ID): Flow<Plan?> {
        return planDao.selectById(planId.value).map { it?.asDomain() }
    }

    override suspend fun create(name: String, weeks: Int): Plan {
        val plan = Plan(name = name, weeks = weeks, days = emptyList())
        planDao.insert(plan.asEntity().plan)
        return plan
    }

    override suspend fun update(planId: ID, name: String, weeks: Int) {
        planDao.update(planId.value, name, weeks)
    }

    override suspend fun delete(planId: ID) {
        planDao.selectById(planId.value).first()?.flatten(
            withPlan = { planDao.delete(it) },
            withDays = { dayDao.delete(it) },
            withExercises = { exerciseDao.delete(it) }
        )
    }
}