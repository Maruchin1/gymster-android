package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.maruchin.data.training.database.model.WeekProgressEntity

@Dao
internal interface WeekProgressDao {

    @Insert
    suspend fun insert(weeks: List<WeekProgressEntity>)

    @Delete
    suspend fun delete(weeks: List<WeekProgressEntity>)
}