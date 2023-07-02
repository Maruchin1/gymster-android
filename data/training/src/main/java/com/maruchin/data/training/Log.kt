package com.maruchin.data.training

import com.maruchin.core.model.ID

data class Log(
    val id: ID = ID.random(),
    val name: String,
    val planName: String,
    val weeks: List<TrainingWeek>,
    val active: Boolean,
) {

    constructor(name: String, plan: Plan) : this(
        name = name,
        planName = plan.name,
        weeks = (1..plan.numOfWeeks).map { weekNumber ->
            TrainingWeek(
                number = weekNumber,
                days = plan.days.map { day ->
                    day.copy(
                        id = ID.random(),
                        exercises = day.exercises.map { exercise ->
                            exercise.copy(
                                id = ID.random(),
                                exerciseSets = exercise.exerciseSets.map {
                                    ExerciseSet()
                                }
                            )
                        }
                    )
                }
            )
        },
        active = false,
    )
}
