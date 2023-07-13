package com.maruchin.data.training.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maruchin.data.training.database.dao.SetProgressDao
import com.maruchin.data.training.database.dao.JournalDao
import com.maruchin.data.training.database.dao.TrainingProgressDao
import com.maruchin.data.training.database.dao.ExerciseProgressDao
import com.maruchin.data.training.database.dao.WeekProgressDao
import com.maruchin.data.training.database.model.ExerciseProgressEntity
import com.maruchin.data.training.database.model.SetProgressEntity
import com.maruchin.data.training.database.model.JournalEntity
import com.maruchin.data.training.database.model.TrainingProgressEntity
import com.maruchin.data.training.database.model.WeekProgressEntity

@Database(
    entities = [
        JournalEntity::class,
        WeekProgressEntity::class,
        TrainingProgressEntity::class,
        ExerciseProgressEntity::class,
        SetProgressEntity::class,
    ],
    version = 1,
)
internal abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    abstract fun weekProgressDao(): WeekProgressDao
    abstract fun trainingProgressDao(): TrainingProgressDao
    abstract fun exerciseProgressDao(): ExerciseProgressDao
    abstract fun setProgressDao(): SetProgressDao
}