package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.training.model.WeekProgress

internal data class WeekProgressWithDays(
    @Embedded
    val week: WeekProgressEntity,
    @Relation(
        entity = TrainingProgressEntity::class,
        parentColumn = "id",
        entityColumn = "weekProgressId"
    )
    val trainingsProgress: List<TrainingProgressWithExercises>,
)

internal fun WeekProgressWithDays.asDomain() = WeekProgress(
    id = week.id,
    trainingsProgress = trainingsProgress.asDomain(),
)

internal fun WeekProgress.asEntity(logId: String) = WeekProgressWithDays(
    week = WeekProgressEntity(
        id = id,
        journalId = logId,
    ),
    trainingsProgress = trainingsProgress.asEntity(id),
)

internal fun List<WeekProgressWithDays>.asDomain() = map { it.asDomain() }

internal fun List<WeekProgress>.asEntity(logId: String) = map { it.asEntity(logId) }