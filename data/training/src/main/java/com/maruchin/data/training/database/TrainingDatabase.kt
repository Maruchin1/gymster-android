package com.maruchin.data.training.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DatabaseLog::class,
        DatabaseTrainingWeek::class,
        DatabaseTrainingDay::class,
        DatabaseExercise::class,
        DatabaseExerciseSet::class,
    ],
    version = 1,
)
internal abstract class TrainingDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
    abstract fun exerciseSetDao(): ExerciseSetDao
}