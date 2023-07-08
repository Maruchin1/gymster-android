package com.maruchin.data.plan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.plan.database.model.PlanEntity
import com.maruchin.data.plan.database.model.PlanWithDays
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PlanDao {

    @Transaction
    @Query("SELECT * FROM plans")
    fun selectAll(): Flow<List<PlanWithDays>>

    @Transaction
    @Query("SELECT * FROM plans WHERE id = :planId")
    fun selectById(planId: String): Flow<PlanWithDays?>

    @Insert
    suspend fun insert(plan: PlanEntity)

    @Query("UPDATE plans SET name = :name, weeks = :weeks WHERE id = :planId")
    suspend fun update(planId: String, name: String, weeks: Int)

    @Delete
    suspend fun delete(plan: PlanEntity)
}