package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingWeek

@Entity(tableName = "training_weeks")
internal data class DatabaseTrainingWeek(
    @PrimaryKey val id: String,
    val number: Int,
    val logId: String,
)

internal data class DatabaseTrainingWeekWithDays(
    @Embedded
    val week: DatabaseTrainingWeek,
    @Relation(
        entity = DatabaseTrainingDay::class,
        parentColumn = "id",
        entityColumn = "trainingWeekId"
    )
    val days: List<DatabaseTrainingDayWithExercises>,
)

internal fun DatabaseTrainingWeekWithDays.toExternal() = TrainingWeek(
    id = ID(week.id),
    number = week.number,
    days = days.toExternal(),
)

internal fun List<DatabaseTrainingWeekWithDays>.toExternal() = map { it.toExternal() }

internal fun TrainingWeek.toDatabase(logId: ID) = DatabaseTrainingWeekWithDays(
    week = DatabaseTrainingWeek(
        id = id.value,
        number = number,
        logId = logId.value,
    ),
    days = days.toDatabase(id),
)

internal fun List<TrainingWeek>.toDatabase(logId: ID) = map { it.toDatabase(logId) }
