package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.maruchin.data.training.database.model.WeekEntity

@Dao
internal interface WeekDao {

    @Insert
    suspend fun insert(weeks: List<WeekEntity>)

    @Delete
    suspend fun delete(weeks: List<WeekEntity>)
}