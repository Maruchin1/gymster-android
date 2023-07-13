package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.maruchin.data.training.database.model.ExerciseProgressEntity

@Dao
internal interface ExerciseProgressDao {

    @Insert
    suspend fun insert(exercises: List<ExerciseProgressEntity>)

    @Delete
    suspend fun delete(exercises: List<ExerciseProgressEntity>)
}