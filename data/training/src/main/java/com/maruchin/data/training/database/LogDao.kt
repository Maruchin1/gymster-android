package com.maruchin.data.training.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
internal abstract class LogDao {

    @Transaction
    @Query("SELECT * FROM logs")
    abstract fun getAll(): Flow<List<DatabaseLogWithWeeks>>

    @Transaction
    @Query("SELECT * FROM logs WHERE id = :logId")
    abstract fun getById(logId: String): Flow<DatabaseLogWithWeeks?>

    @Transaction
    @Query("SELECT * FROM logs WHERE active = 1 LIMIT 1")
    abstract fun getActive(): Flow<DatabaseLogWithWeeks?>

    @Transaction
    open suspend fun insert(log: DatabaseLogWithWeeks) {
        insertLog(log.log)
        insertWeeks(log.weeks.map { it.week })
        log.weeks.flatMap { it.days }.let { daysWithExercises ->
            insertDays(daysWithExercises.map { it.day })
            daysWithExercises.flatMap { it.exercises }.let { exercisesWithSeries ->
                insertExercises(exercisesWithSeries.map { it.exercise })
                insertSeries(exercisesWithSeries.flatMap { it.series })
            }
        }
    }

    @Transaction
    open suspend fun activate(logId: String) {
        setInactiveAll()
        setActiveById(logId)
    }

    @Transaction
    open suspend fun delete(logId: String) {
        getById(logId).first()?.let { log ->
            deleteLog(log.log)
            deleteWeeks(log.weeks.map { it.week })
            log.weeks.flatMap { it.days }.let { daysWithExercises ->
                deleteDays(daysWithExercises.map { it.day })
                daysWithExercises.flatMap { it.exercises }.let { exercisesWithSeries ->
                    deleteExercises(exercisesWithSeries.map { it.exercise })
                    deleteSeries(exercisesWithSeries.flatMap { it.series })
                }
            }
        }
    }

    @Insert
    protected abstract suspend fun insertLog(log: DatabaseLog)

    @Insert
    protected abstract suspend fun insertWeeks(weeks: List<DatabaseTrainingWeek>)

    @Insert
    protected abstract suspend fun insertDays(days: List<DatabaseTrainingDay>)

    @Insert
    protected abstract suspend fun insertExercises(exercises: List<DatabaseExercise>)

    @Insert
    protected abstract suspend fun insertSeries(series: List<DatabaseExerciseSet>)

    @Delete
    protected abstract suspend fun deleteLog(log: DatabaseLog)

    @Delete
    protected abstract suspend fun deleteWeeks(weeks: List<DatabaseTrainingWeek>)

    @Delete
    protected abstract suspend fun deleteDays(days: List<DatabaseTrainingDay>)

    @Delete
    protected abstract suspend fun deleteExercises(exercises: List<DatabaseExercise>)

    @Delete
    protected abstract suspend fun deleteSeries(series: List<DatabaseExerciseSet>)

    @Query("UPDATE logs SET active = 0 WHERE active = 1")
    protected abstract suspend fun setInactiveAll()

    @Query("UPDATE logs SET active = 1 WHERE id = :logId")
    protected abstract suspend fun setActiveById(logId: String)
}