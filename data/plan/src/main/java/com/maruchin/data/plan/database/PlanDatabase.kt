package com.maruchin.data.plan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maruchin.data.plan.database.dao.DayDao
import com.maruchin.data.plan.database.dao.ExerciseDao
import com.maruchin.data.plan.database.dao.PlanDao
import com.maruchin.data.plan.database.model.PlanEntity
import com.maruchin.data.plan.database.model.DayEntity
import com.maruchin.data.plan.database.model.ExerciseEntity

@Database(
    entities = [PlanEntity::class, DayEntity::class, ExerciseEntity::class],
    version = 1
)
internal abstract class PlanDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun dayDao(): DayDao
    abstract fun exerciseDao(): ExerciseDao
}