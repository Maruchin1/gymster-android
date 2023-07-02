package com.maruchin.data.training.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ExerciseSetDao {

    @Query("SELECT * FROM sets WHERE id = :setId")
    fun getById(setId: String): Flow<DatabaseExerciseSet?>

    @Query("UPDATE sets SET weight = :weight, reps = :reps WHERE id = :setId")
    suspend fun update(setId: String, weight: Float?, reps: Int?)
}