package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalWeek

internal data class WeekEntityWithDays(
    @Embedded
    val week: WeekEntity,
    @Relation(
        entity = DayEntity::class,
        parentColumn = "id",
        entityColumn = "weekId"
    )
    val days: List<DayEntityWithExercises>,
)

internal fun WeekEntityWithDays.asDomain() = JournalWeek(
    id = ID(week.id),
    number = week.number,
    days = days.asDomain(),
)

internal fun JournalWeek.asEntity(logId: ID) = WeekEntityWithDays(
    week = WeekEntity(
        id = id.value,
        number = number,
        journalId = logId.value,
    ),
    days = days.asEntity(id),
)

internal fun List<WeekEntityWithDays>.asDomain() = map { it.asDomain() }

internal fun List<JournalWeek>.asEntity(logId: ID) = map { it.asEntity(logId) }