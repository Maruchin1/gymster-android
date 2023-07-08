package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.training.database.model.JournalEntity
import com.maruchin.data.training.database.model.JournalEntityWithWeeks
import kotlinx.coroutines.flow.Flow

@Dao
internal interface JournalDao {

    @Transaction
    @Query("SELECT * FROM journals")
    fun getAll(): Flow<List<JournalEntityWithWeeks>>

    @Transaction
    @Query("SELECT * FROM journals WHERE id = :journalId")
    fun getById(journalId: String): Flow<JournalEntityWithWeeks?>

    @Transaction
    @Query("SELECT * FROM journals WHERE active = 1 LIMIT 1")
    fun getActive(): Flow<JournalEntityWithWeeks?>

    @Insert
    suspend fun insert(journal: JournalEntity)

    @Transaction
    suspend fun activate(logId: String) {
        setInactiveAll()
        setActiveById(logId)
    }

    @Query("UPDATE journals SET activeWeekId = :weekId WHERE id = :logId")
    suspend fun changeActiveWeek(logId: String, weekId: String)

    @Query("UPDATE journals SET active = 0 WHERE active = 1")
    suspend fun setInactiveAll()

    @Query("UPDATE journals SET active = 1 WHERE id = :logId")
    suspend fun setActiveById(logId: String)

    @Delete
    suspend fun delete(journal: JournalEntity)
}