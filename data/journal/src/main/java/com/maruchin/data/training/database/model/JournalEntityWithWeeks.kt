package com.maruchin.data.training.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.core.model.ID
import com.maruchin.data.training.model.Journal

internal data class JournalEntityWithWeeks(
    @Embedded
    val journal: JournalEntity,
    @Relation(entity = WeekEntity::class, parentColumn = "id", entityColumn = "journalId")
    val weeks: List<WeekEntityWithDays>
)

internal fun JournalEntityWithWeeks.asDomain() = Journal(
    id = ID(journal.id),
    name = journal.name,
    planName = journal.planName,
    weeks = weeks.asDomain(),
    active = journal.active,
    currentWeekId = ID(journal.activeWeekId),
)

internal fun Journal.asEntity() = JournalEntityWithWeeks(
    journal = JournalEntity(
        id = id.value,
        name = name,
        planName = planName,
        active = active,
        activeWeekId = currentWeekId.value,
    ),
    weeks = weeks.asEntity(id),
)

internal fun List<JournalEntityWithWeeks>.asDomain() = map { it.asDomain() }

internal inline fun JournalEntityWithWeeks.flatten(
    withJournal: (JournalEntity) -> Unit,
    withWeeks: (List<WeekEntity>) -> Unit,
    withDays: (List<DayEntity>) -> Unit,
    withExercises: (List<ExerciseEntity>) -> Unit,
    withSets: (List<SetEntity>) -> Unit,
) {
    journal.let(withJournal)
    weeks.let { weeksWithDays ->
        weeksWithDays.map { it.week }.let(withWeeks)
        weeksWithDays.flatMap { it.days }.let { daysWithExercises ->
            daysWithExercises.map { it.day }.let(withDays)
            daysWithExercises.flatMap { it.exercises }.let { exercisesWithSets ->
                exercisesWithSets.map { it.exercise }.let(withExercises)
                exercisesWithSets.flatMap { it.sets }.let(withSets)
            }
        }
    }
}
