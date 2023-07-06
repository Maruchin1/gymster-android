package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.training.database.model.DatabaseTrainingDayWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TrainingDayDao {

    @Transaction
    @Query("SELECT * FROM training_days WHERE id = :trainingDayId")
    fun getById(trainingDayId: String): Flow<DatabaseTrainingDayWithExercises?>
}