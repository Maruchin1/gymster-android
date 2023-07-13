package com.maruchin.data.training.model

import com.maruchin.data.plan.model.Plan
import com.maruchin.data.plan.model.samplePlan
import java.util.UUID

data class Journal(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val planName: String,
    val weeksProgress: List<WeekProgress>,
) {

    val fullName: String
        get() = "$planName - $name"

    constructor(name: String, plan: Plan) : this(
        name = name,
        planName = plan.name,
        weeksProgress = plan.weeks.map { WeekProgress(it) }
    )
}

val sampleEmptyJournal = Journal(name = "Q1/23", plan = samplePlan)

val sampleCompleteJournal = sampleEmptyJournal.copy(
    weeksProgress = sampleEmptyJournal.weeksProgress.map { week ->
        week.copy(
            trainingsProgress = week.trainingsProgress.map { day ->
                day.copy(
                    exercisesProgress = day.exercisesProgress.map { exercise ->
                        exercise.copy(
                            setsProgress = exercise.setsProgress.map { set ->
                                set.copy(weight = 22.5f, reps = 12)
                            }
                        )
                    }
                )
            }
        )
    }
)
