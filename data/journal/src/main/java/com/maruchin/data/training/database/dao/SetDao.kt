package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.maruchin.data.training.database.model.SetEntity

@Dao
internal interface SetDao {

    @Insert
    suspend fun insert(sets: List<SetEntity>)

    @Query("UPDATE sets SET weight = :weight WHERE id = :setId")
    suspend fun updateWeight(setId: String, weight: Float?)

    @Query("UPDATE sets SET reps = :reps WHERE id = :setId")
    suspend fun updateReps(setId: String, reps: Int?)

    @Delete
    suspend fun delete(sets: List<SetEntity>)
}