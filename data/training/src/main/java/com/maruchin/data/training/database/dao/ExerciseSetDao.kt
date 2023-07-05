package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
internal interface ExerciseSetDao {

    @Query("UPDATE sets SET weight = :weight WHERE id = :setId")
    suspend fun updateWeight(setId: String, weight: Float?)

    @Query("UPDATE sets SET reps = :reps WHERE id = :setId")
    suspend fun updateReps(setId: String, reps: Int?)
}