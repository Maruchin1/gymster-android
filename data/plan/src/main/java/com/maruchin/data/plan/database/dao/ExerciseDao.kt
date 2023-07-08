package com.maruchin.data.plan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.maruchin.data.plan.database.model.ExerciseEntity

@Dao
internal interface ExerciseDao {

    @Insert
    suspend fun insert(exercise: ExerciseEntity)

    @Query("UPDATE exercises SET number = :number, name = :name, sets = :sets, minReps = :minReps, maxReps = :maxReps WHERE id = :exerciseId")
    suspend fun update(
        exerciseId: String,
        number: String,
        name: String,
        sets: Int,
        minReps: Int,
        maxReps: Int
    )

    @Query("DELETE FROM exercises WHERE id = :exerciseId")
    suspend fun deleteById(exerciseId: String)

    @Delete
    suspend fun delete(exercises: List<ExerciseEntity>)
}