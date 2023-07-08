package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.database.dao.DayDao
import com.maruchin.data.plan.database.model.asEntity
import com.maruchin.data.plan.model.PlanDay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPlanDayRepository @Inject constructor(
    private val dayDao: DayDao,
) : PlanDayRepository {

    override suspend fun create(planId: ID, name: String): PlanDay {
        val day = PlanDay(name = name, exercises = emptyList())
        val databaseDay = day.asEntity(planId.value)
        dayDao.insert(databaseDay.day)
        return day
    }

    override suspend fun update(dayId: ID, name: String) {
        dayDao.update(dayId.value, name)
    }

    override suspend fun delete(dayId: ID) {
        dayDao.deleteById(dayId.value)
    }
}