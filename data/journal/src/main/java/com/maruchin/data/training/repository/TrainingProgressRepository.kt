package com.maruchin.data.training.repository

import com.maruchin.data.training.model.TrainingProgress
import kotlinx.coroutines.flow.Flow

interface TrainingProgressRepository {

    fun getById(dayId: String): Flow<TrainingProgress?>
}