package com.maruchin.data.plan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.maruchin.data.plan.database.model.DayEntity

@Dao
internal interface DayDao {

    @Insert
    suspend fun insert(day: DayEntity)

    @Query("UPDATE days SET name = :name WHERE id = :dayId")
    suspend fun update(dayId: String, name: String)

    @Query("DELETE FROM days WHERE id = :dayId")
    suspend fun deleteById(dayId: String)

    @Delete
    suspend fun delete(days: List<DayEntity>)
}