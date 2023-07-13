package com.maruchin.data.training.repository

import com.maruchin.data.plan.model.Plan
import com.maruchin.data.training.database.dao.TrainingProgressDao
import com.maruchin.data.training.database.dao.ExerciseProgressDao
import com.maruchin.data.training.database.dao.JournalDao
import com.maruchin.data.training.database.dao.SetProgressDao
import com.maruchin.data.training.database.dao.WeekProgressDao
import com.maruchin.data.training.database.model.asEntity
import com.maruchin.data.training.database.model.asDomain
import com.maruchin.data.training.database.model.flatten
import com.maruchin.data.training.datastore.JournalDataStore
import com.maruchin.data.training.model.Journal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultJournalRepository @Inject constructor(
    private val journalDao: JournalDao,
    private val weekProgressDao: WeekProgressDao,
    private val trainingProgressDao: TrainingProgressDao,
    private val exerciseProgressDao: ExerciseProgressDao,
    private val setProgressDao: SetProgressDao,
    private val journalDataStore: JournalDataStore,
) : JournalRepository {

    override fun getAll(): Flow<List<Journal>> {
        return journalDao.getAll().map { it.asDomain() }
    }

    override fun getActive(): Flow<Journal?> {
        return journalDataStore.getActivePlanId().flatMapLatest { journalId ->
            journalId?.let { journalDao.getById(it) } ?: flowOf(null)
        }.map { it?.asDomain() }
    }

    override suspend fun create(name: String, plan: Plan): String {
        val journal = Journal(name = name, plan = plan)
        journal.asEntity().flatten(
            withJournal = { journalDao.insert(it) },
            withWeeks = { weekProgressDao.insert(it) },
            withDays = { trainingProgressDao.insert(it) },
            withExercises = { exerciseProgressDao.insert(it) },
            withSets = { setProgressDao.insert(it) },
        )
        return journal.id
    }

    override suspend fun setActive(journalId: String) {
        journalDataStore.setActivePlanId(journalId)
    }

    override suspend fun delete(journalId: String) {
        journalDao.getById(journalId).first()?.flatten(
            withJournal = { journalDao.delete(it) },
            withWeeks = { weekProgressDao.delete(it) },
            withDays = { trainingProgressDao.delete(it) },
            withExercises = { exerciseProgressDao.delete(it) },
            withSets = { setProgressDao.delete(it) },
        )
    }
}
