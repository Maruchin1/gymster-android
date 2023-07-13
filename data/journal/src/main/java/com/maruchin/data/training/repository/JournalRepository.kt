package com.maruchin.data.training.repository

import com.maruchin.data.plan.model.Plan
import com.maruchin.data.training.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    fun getAll(): Flow<List<Journal>>

    fun getActive(): Flow<Journal?>

    suspend fun create(name: String, plan: Plan): String

    suspend fun setActive(journalId: String)

    suspend fun delete(journalId: String)
}