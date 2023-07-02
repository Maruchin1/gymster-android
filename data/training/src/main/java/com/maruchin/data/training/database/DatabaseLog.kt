package com.maruchin.data.training.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.Log

@Entity(tableName = "logs")
internal data class DatabaseLog(
    @PrimaryKey val id: String,
    val name: String,
    val planName: String,
    val active: Boolean,
)

internal data class DatabaseLogWithWeeks(
    @Embedded
    val log: DatabaseLog,
    @Relation(entity = DatabaseTrainingWeek::class, parentColumn = "id", entityColumn = "logId")
    val weeks: List<DatabaseTrainingWeekWithDays>
)

internal fun DatabaseLogWithWeeks.toExternal() = Log(
    id = ID(log.id),
    name = log.name,
    planName = log.planName,
    weeks = weeks.toExternal(),
    active = log.active,
)

internal fun List<DatabaseLogWithWeeks>.toExternal() = map { it.toExternal() }

internal fun Log.toDatabase() = DatabaseLogWithWeeks(
    log = DatabaseLog(
        id = id.value,
        name = name,
        planName = planName,
        active = active,
    ),
    weeks = weeks.toDatabase(id),
)
