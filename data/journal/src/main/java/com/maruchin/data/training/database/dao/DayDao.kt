package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.training.database.model.DayEntity
import com.maruchin.data.training.database.model.DayEntityWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DayDao {

    @Transaction
    @Query("SELECT * FROM days WHERE id = :trainingDayId")
    fun getById(trainingDayId: String): Flow<DayEntityWithExercises?>

    @Insert
    suspend fun insert(days: List<DayEntity>)

    @Delete
    suspend fun delete(days: List<DayEntity>)
}