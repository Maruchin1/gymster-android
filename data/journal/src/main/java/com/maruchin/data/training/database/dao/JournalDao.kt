package com.maruchin.data.training.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.maruchin.data.training.database.model.JournalEntity
import com.maruchin.data.training.database.model.JournalWithWeeks
import kotlinx.coroutines.flow.Flow

@Dao
internal interface JournalDao {

    @Transaction
    @Query("SELECT * FROM JournalEntity")
    fun getAll(): Flow<List<JournalWithWeeks>>

    @Transaction
    @Query("SELECT * FROM JournalEntity WHERE id = :journalId")
    fun getById(journalId: String): Flow<JournalWithWeeks?>

    @Insert
    suspend fun insert(journal: JournalEntity)

    @Delete
    suspend fun delete(journal: JournalEntity)
}