package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.database.dao.DayDao
import com.maruchin.data.training.database.model.asDomain
import com.maruchin.data.training.model.JournalDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultJournalDayRepository @Inject constructor(
    private val dayDao: DayDao,
) : JournalDayRepository {

    override fun getById(dayId: ID): Flow<JournalDay?> {
        return dayDao.getById(dayId.value).map { it?.asDomain() }
    }
}