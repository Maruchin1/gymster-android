package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.data.training.model.Journal

internal data class JournalWithWeeks(
    @Embedded
    val journal: JournalEntity,
    @Relation(entity = WeekProgressEntity::class, parentColumn = "id", entityColumn = "journalId")
    val weeksProgress: List<WeekProgressWithDays>
)

internal fun JournalWithWeeks.asDomain() = Journal(
    id = journal.id,
    name = journal.name,
    planName = journal.planName,
    weeksProgress = weeksProgress.asDomain(),
)

internal fun Journal.asEntity() = JournalWithWeeks(
    journal = JournalEntity(
        id = id,
        name = name,
        planName = planName,
    ),
    weeksProgress = weeksProgress.asEntity(id),
)

internal fun List<JournalWithWeeks>.asDomain() = map { it.asDomain() }

internal inline fun JournalWithWeeks.flatten(
    withJournal: (JournalEntity) -> Unit,
    withWeeks: (List<WeekProgressEntity>) -> Unit,
    withDays: (List<TrainingProgressEntity>) -> Unit,
    withExercises: (List<ExerciseProgressEntity>) -> Unit,
    withSets: (List<SetProgressEntity>) -> Unit,
) {
    journal.let(withJournal)
    weeksProgress.let { weeksWithDays ->
        weeksWithDays.map { it.week }.let(withWeeks)
        weeksWithDays.flatMap { it.trainingsProgress }.let { daysWithExercises ->
            daysWithExercises.map { it.training }.let(withDays)
            daysWithExercises.flatMap { it.exercisesProgress }.let { exercisesWithSets ->
                exercisesWithSets.map { it.exercise }.let(withExercises)
                exercisesWithSets.flatMap { it.setsProgress }.let(withSets)
            }
        }
    }
}
