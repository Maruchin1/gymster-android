package com.maruchin.data.training.repository

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingDay
import kotlinx.coroutines.flow.Flow

interface TrainingDayRepository {

    fun getById(trainingDayId: ID): Flow<TrainingDay?>
}