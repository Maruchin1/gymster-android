package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalDay
import kotlinx.coroutines.flow.Flow

interface JournalDayRepository {

    fun getById(dayId: ID): Flow<JournalDay?>
}