package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.training.database.dao.DayDao
import com.maruchin.data.training.database.dao.ExerciseDao
import com.maruchin.data.training.database.dao.JournalDao
import com.maruchin.data.training.database.dao.SetDao
import com.maruchin.data.training.database.dao.WeekDao
import com.maruchin.data.training.database.model.asEntity
import com.maruchin.data.training.database.model.asDomain
import com.maruchin.data.training.database.model.flatten
import com.maruchin.data.training.model.Journal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultJournalRepository @Inject constructor(
    private val journalDao: JournalDao,
    private val weekDao: WeekDao,
    private val dayDao: DayDao,
    private val exerciseDao: ExerciseDao,
    private val setDao: SetDao,
) : JournalRepository {

    override fun getAll(): Flow<List<Journal>> {
        return journalDao.getAll().map { it.asDomain() }
    }

    override fun getActive(): Flow<Journal?> {
        return journalDao.getActive().map { it?.asDomain() }
    }

    override suspend fun create(name: String, plan: Plan): Journal {
        val journal = Journal(name = name, plan = plan)
        journal.asEntity().flatten(
            withJournal = { journalDao.insert(it) },
            withWeeks = { weekDao.insert(it) },
            withDays = { dayDao.insert(it) },
            withExercises = { exerciseDao.insert(it) },
            withSets = { setDao.insert(it) },
        )
        return journal
    }

    override suspend fun activate(journalId: ID) {
        journalDao.activate(journalId.value)
    }

    override suspend fun changeCurrentWeek(journalId: ID, weekID: ID) {
        journalDao.changeActiveWeek(journalId.value, weekID.value)
    }

    override suspend fun delete(journalId: ID) {
        journalDao.getById(journalId.value).first()?.flatten(
            withJournal = { journalDao.delete(it) },
            withWeeks = { weekDao.delete(it) },
            withDays = { dayDao.delete(it) },
            withExercises = { exerciseDao.delete(it) },
            withSets = { setDao.delete(it) },
        )
    }
}
