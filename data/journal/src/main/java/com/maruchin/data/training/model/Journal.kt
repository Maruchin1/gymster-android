package com.maruchin.data.training.model

import com.maruchin.core.model.ID
import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan

data class Journal(
    val id: ID = ID.random,
    val name: String,
    val planName: String,
    val weeks: List<JournalWeek>,
    val active: Boolean = false,
    val currentWeekId: ID = weeks.first().id,
) {

    constructor(name: String, plan: Plan) : this(
        name = name,
        planName = plan.name,
        weeks = (1..plan.weeks).map { JournalWeek(it, plan.days) }
    )
}

val sampleEmptyJournal = Journal(name = "Q1 2023", plan = samplePlan)

val sampleCompleteJournal = sampleEmptyJournal.copy(
    weeks = sampleEmptyJournal.weeks.map { week ->
        week.copy(
            days = week.days.map { day ->
                day.copy(
                    exercises = day.exercises.map { exercise ->
                        exercise.copy(
                            sets = exercise.sets.map { set ->
                                set.copy(weight = 22.5f, reps = 12)
                            }
                        )
                    }
                )
            }
        )
    }
)
