package com.maruchin.data.training.model

import com.maruchin.core.model.ID

data class Plan(
    val id: ID = ID.random,
    val name: String,
    val numOfWeeks: Int,
    val days: List<TrainingDay>,
)

val samplePlan = Plan(
    name = "Poziom 3 Klatka Plecy",
    numOfWeeks = 12,
    days = listOf(
        TrainingDay(
            name = "PUSH SIŁA",
            exercises = listOf(
                Exercise(
                    number = "1",
                    name = "Wyciskanie sztangi na ławce poziomej",
                    repsRange = 4..6,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "2",
                    name = "Wyciskanie hantli na ławce skośnej",
                    repsRange = 6..8,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "3",
                    name = "Rozpiętki hantlami",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4A",
                    name = "Prostowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4B",
                    name = "Wznosy bokiem na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5",
                    name = "Przysiad z hantlem",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                )
            )
        ),
        TrainingDay(
            name = "PULL SIŁA",
            exercises = listOf(
                Exercise(
                    number = "1",
                    name = "Wiosłowanie sztangi nachwytem",
                    repsRange = 4..6,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "2",
                    name = "Sciąganie drążka na wyciągu",
                    repsRange = 6..8,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "3",
                    name = "Wiosłowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4",
                    name = "Rumuński martwy ciąg hantlami",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5A",
                    name = "Uginanie hantli stojąc",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5B",
                    name = "Odwrotne rozpiętki na bramie",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                )
            )
        ),
        TrainingDay(
            name = "PUSH HYPER",
            exercises = listOf(
                Exercise(
                    number = "1",
                    name = "Wyciskanie sztangi na ławce poziomej",
                    repsRange = 8..10,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "2",
                    name = "Wyciskanie hantli na ławce skośnej",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "3",
                    name = "Rozpiętki hantlami",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4A",
                    name = "Prostowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4B",
                    name = "Wznosy bokiem na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5",
                    name = "Przysiad z hantlem",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                )
            )
        ),
        TrainingDay(
            name = "PULL HYPER",
            exercises = listOf(
                Exercise(
                    number = "1",
                    name = "Wiosłowanie sztangi nachwytem",
                    repsRange = 8..10,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "2",
                    name = "Sciąganie drążka na wyciągu",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "3",
                    name = "Wiosłowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "4",
                    name = "Rumuński martwy ciąg hantlami",
                    repsRange = 10..12,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5A",
                    name = "Uginanie hantli stojąc",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                ),
                Exercise(
                    number = "5B",
                    name = "Odwrotne rozpiętki na bramie",
                    repsRange = 12..15,
                    sets = listOf(
                        ExerciseSet(),
                        ExerciseSet(),
                        ExerciseSet(),
                    )
                )
            )
        ),
    )
)
