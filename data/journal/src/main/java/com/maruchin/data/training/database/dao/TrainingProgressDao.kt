package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.training.database.model.TrainingProgressEntity
import com.maruchin.data.training.database.model.TrainingProgressWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TrainingProgressDao {

    @Transaction
    @Query("SELECT * FROM TrainingProgressEntity WHERE id = :trainingDayId")
    fun getById(trainingDayId: String): Flow<TrainingProgressWithExercises?>

    @Insert
    suspend fun insert(days: List<TrainingProgressEntity>)

    @Delete
    suspend fun delete(days: List<TrainingProgressEntity>)
}