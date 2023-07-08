package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.maruchin.data.training.database.model.ExerciseEntity

@Dao
internal interface ExerciseDao {

    @Insert
    suspend fun insert(exercises: List<ExerciseEntity>)

    @Delete
    suspend fun delete(exercises: List<ExerciseEntity>)
}