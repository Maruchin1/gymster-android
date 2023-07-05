package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.TrainingLog

@Entity(tableName = "logs")
internal data class DatabaseLog(
    @PrimaryKey val id: String,
    val name: String,
    val planName: String,
    val active: Boolean,
    val activeWeekId: String,
)

internal data class DatabaseLogWithWeeks(
    @Embedded
    val log: DatabaseLog,
    @Relation(entity = DatabaseTrainingWeek::class, parentColumn = "id", entityColumn = "logId")
    val weeks: List<DatabaseTrainingWeekWithDays>
)

internal fun DatabaseLogWithWeeks.toExternal() = TrainingLog(
    id = ID(log.id),
    name = log.name,
    planName = log.planName,
    weeks = weeks.toExternal(),
    active = log.active,
    currentWeekId = ID(log.activeWeekId),
)

internal fun List<DatabaseLogWithWeeks>.toExternal() = map { it.toExternal() }

internal fun TrainingLog.toDatabase() = DatabaseLogWithWeeks(
    log = DatabaseLog(
        id = id.value,
        name = name,
        planName = planName,
        active = active,
        activeWeekId = currentWeekId.value,
    ),
    weeks = weeks.toDatabase(id),
)
