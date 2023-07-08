package com.maruchin.data.training.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maruchin.data.training.database.dao.SetDao
import com.maruchin.data.training.database.dao.JournalDao
import com.maruchin.data.training.database.dao.DayDao
import com.maruchin.data.training.database.dao.ExerciseDao
import com.maruchin.data.training.database.dao.WeekDao
import com.maruchin.data.training.database.model.ExerciseEntity
import com.maruchin.data.training.database.model.SetEntity
import com.maruchin.data.training.database.model.JournalEntity
import com.maruchin.data.training.database.model.DayEntity
import com.maruchin.data.training.database.model.WeekEntity

@Database(
    entities = [
        JournalEntity::class,
        WeekEntity::class,
        DayEntity::class,
        ExerciseEntity::class,
        SetEntity::class,
    ],
    version = 1,
)
internal abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    abstract fun weekDao(): WeekDao
    abstract fun dayDao(): DayDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setDao(): SetDao
}