package com.maruchin.data.training.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maruchin.data.training.database.dao.ExerciseSetDao
import com.maruchin.data.training.database.dao.TrainingLogDao
import com.maruchin.data.training.database.dao.TrainingDayDao
import com.maruchin.data.training.database.model.DatabaseExercise
import com.maruchin.data.training.database.model.DatabaseExerciseSet
import com.maruchin.data.training.database.model.DatabaseTrainingLog
import com.maruchin.data.training.database.model.DatabaseTrainingDay
import com.maruchin.data.training.database.model.DatabaseTrainingWeek

@Database(
    entities = [
        DatabaseTrainingLog::class,
        DatabaseTrainingWeek::class,
        DatabaseTrainingDay::class,
        DatabaseExercise::class,
        DatabaseExerciseSet::class,
    ],
    version = 1,
)
internal abstract class TrainingDatabase : RoomDatabase() {

    abstract fun trainingLogDao(): TrainingLogDao

    abstract fun trainingDayDao(): TrainingDayDao

    abstract fun exerciseSetDao(): ExerciseSetDao
}