package com.maruchin.data.plan.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.PlanDay

interface PlanDayRepository {

    suspend fun create(planId: ID, name: String): PlanDay

    suspend fun update(dayId: ID, name: String)

    suspend fun delete(dayId: ID)
}