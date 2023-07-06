package com.maruchin.data.training.model

import com.maruchin.core.model.ID

data class TrainingLog(
    val id: ID = ID.random,
    val name: String,
    val planName: String,
    val weeks: List<TrainingWeek>,
    val active: Boolean,
    val currentWeekId: ID = weeks.first().id,
) {

    constructor(name: String, trainingPlan: TrainingPlan) : this(
        name = name,
        planName = trainingPlan.name,
        weeks = (1..trainingPlan.numOfWeeks).map { weekNumber ->
            TrainingWeek(
                number = weekNumber,
                days = trainingPlan.days.map { day ->
                    day.copy(
                        id = ID.random,
                        exercises = day.exercises.map { exercise ->
                            exercise.copy(
                                id = ID.random,
                                sets = exercise.sets.map {
                                    ExerciseSet()
                                }
                            )
                        }
                    )
                },
            )
        },
        active = false,
    )
}

val sampleTrainingLog = TrainingLog(name = "Q1 2023", trainingPlan = sampleTrainingPlan).let { log ->
    log.copy(
        weeks = log.weeks.map { week ->
            week.copy(
                days = week.days.map { day ->
                    day.copy(
                        exercises = day.exercises.map { exercise ->
                            exercise.copy(
                                sets = exercise.sets.map { set ->
                                    set.copy(
                                        weight = 62.5f,
                                        reps = 10
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

val sampleEmptyTrainingLog = TrainingLog("Q2 2023", trainingPlan = sampleTrainingPlan)
