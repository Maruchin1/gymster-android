package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.training.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    fun getAll(): Flow<List<Journal>>

    fun getActive(): Flow<Journal?>

    suspend fun create(name: String, plan: Plan): Journal

    suspend fun activate(journalId: ID)

    suspend fun changeCurrentWeek(journalId: ID, weekID: ID)

    suspend fun delete(journalId: ID)
}