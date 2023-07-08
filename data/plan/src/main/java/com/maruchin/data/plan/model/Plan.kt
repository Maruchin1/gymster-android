package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class Plan(
    val id: ID = ID.random,
    val name: String,
    val weeks: Int,
    val days: List<PlanDay>
)

val samplePlan = Plan(
    name = "Poziom 3 Klatka Plecy",
    weeks = 12,
    days = listOf(
        PlanDay(
            name = "PUSH SIŁA",
            exercises = listOf(
                PlanExercise(
                    number = "1",
                    name = "Wyciskanie sztangi na ławce poziomej",
                    repsRange = 4..6,
                    sets = 3
                ),
                PlanExercise(
                    number = "2",
                    name = "Wyciskanie hantli na ławce skośnej",
                    repsRange = 6..8,
                    sets = 3
                ),
                PlanExercise(
                    number = "3",
                    name = "Rozpiętki hantlami",
                    repsRange = 12..15,
                    sets = 2
                ),
                PlanExercise(
                    number = "4A",
                    name = "Prostowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "4B",
                    name = "Wznosy bokiem na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "5",
                    name = "Przysiad z hantlem",
                    repsRange = 10..12,
                    sets = 2
                )
            )
        ),
        PlanDay(
            name = "PULL SIŁA",
            exercises = listOf(
                PlanExercise(
                    number = "1",
                    name = "Wiosłowanie sztangi nachwytem",
                    repsRange = 4..6,
                    sets = 3
                ),
                PlanExercise(
                    number = "2",
                    name = "Sciąganie drążka na wyciągu",
                    repsRange = 6..8,
                    sets = 3
                ),
                PlanExercise(
                    number = "3",
                    name = "Wiosłowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 2
                ),
                PlanExercise(
                    number = "4",
                    name = "Rumuński martwy ciąg hantlami",
                    repsRange = 10..12,
                    sets = 2
                ),
                PlanExercise(
                    number = "5A",
                    name = "Uginanie hantli stojąc",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "5B",
                    name = "Odwrotne rozpiętki na bramie",
                    repsRange = 12..15,
                    sets = 3
                )
            )
        ),
        PlanDay(
            name = "PUSH HYPER",
            exercises = listOf(
                PlanExercise(
                    number = "1",
                    name = "Wyciskanie sztangi na ławce poziomej",
                    repsRange = 8..10,
                    sets = 3
                ),
                PlanExercise(
                    number = "2",
                    name = "Wyciskanie hantli na ławce skośnej",
                    repsRange = 10..12,
                    sets = 3
                ),
                PlanExercise(
                    number = "3",
                    name = "Rozpiętki hantlami",
                    repsRange = 12..15,
                    sets = 2
                ),
                PlanExercise(
                    number = "4A",
                    name = "Prostowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "4B",
                    name = "Wznosy bokiem na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "5",
                    name = "Przysiad z hantlem",
                    repsRange = 10..12,
                    sets = 2
                )
            )
        ),
        PlanDay(
            name = "PULL HYPER",
            exercises = listOf(
                PlanExercise(
                    number = "1",
                    name = "Wiosłowanie sztangi nachwytem",
                    repsRange = 8..10,
                    sets = 3
                ),
                PlanExercise(
                    number = "2",
                    name = "Sciąganie drążka na wyciągu",
                    repsRange = 10..12,
                    sets = 3
                ),
                PlanExercise(
                    number = "3",
                    name = "Wiosłowanie na wyciągu jednorącz",
                    repsRange = 12..15,
                    sets = 2
                ),
                PlanExercise(
                    number = "4",
                    name = "Rumuński martwy ciąg hantlami",
                    repsRange = 10..12,
                    sets = 2
                ),
                PlanExercise(
                    number = "5A",
                    name = "Uginanie hantli stojąc",
                    repsRange = 12..15,
                    sets = 3
                ),
                PlanExercise(
                    number = "5B",
                    name = "Odwrotne rozpiętki na bramie",
                    repsRange = 12..15,
                    sets = 3
                )
            )
        ),
    )
)
