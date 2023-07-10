package com.maruchin.data.plan.model

import com.maruchin.core.model.ID

data class Plan(
    val id: ID = ID.random,
    val name: String,
    val weeks: List<Week>,
)

val samplePlan = Plan(
    name = "Poziom 3 Ogólny",
    weeks = (1..6).map {
        Week(
            trainings = listOf(
                Training(
                    name = "TRENING 1 - PUSH 1",
                    exercises = listOf(
                        Exercise(
                            name = "Wyciskanie sztangi na ławce poziomej",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 4..6,
                            rest = "2-3min",
                        ),
                        Exercise(
                            name = "Rozpiętki hantlami na ławce skos dodatni",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            name = "Wyciskanie hantli nad głowę siedzac",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 8..10,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            name = "Wznosy hantli bokiem stojąc",
                            sets = Sets(standard = 1, drop = 3),
                            repsRange = 10..20,
                            rest = "30sek",
                        ),
                        Exercise(
                            name = "Prostowanie ramion na wyciągu",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            name = "Wybierz dowolne ćwiczenie z playlisty z filmu",
                            sets = Sets(standard = 1, drop = 2),
                            repsRange = 10..20,
                            rest = "30sek",
                        )
                    )
                ),
                Training(
                    name = "TRENING 2 - PULL 1",
                    exercises = listOf(
                        Exercise(
                            name = "Wiosłowanie sztangą",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 6..8,
                            rest = "2-3min",
                        ),
                        Exercise(
                            name = "Sciąganie drążka wyciągu górnego chwytem V",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 8..10,
                            rest = "2-3min"
                        ),
                        Exercise(
                            name = "Wiosłowanie hantlami w oparciu o ławkę",
                            sets = Sets(standard = 2, drop = 0),
                            repsRange = 10..12,
                            rest = "2-3min"
                        ),
                        Exercise(
                            name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                            sets = Sets(standard = 1, drop = 2),
                            repsRange = 10..20,
                            rest = "30sek"
                        ),
                        Exercise(
                            name = "Uginanie hantli nad modlitewniku",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek"
                        ),
                        Exercise(
                            name = "Wybierz dowolne ćwiczenie z playlisty z filmu",
                            sets = Sets(standard = 1, drop = 2),
                            repsRange = 10..20,
                            rest = "30sek"
                        )
                    )
                )
            )
        )
    }
)
