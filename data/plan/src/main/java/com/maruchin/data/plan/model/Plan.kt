package com.maruchin.data.plan.model

import java.util.UUID

data class Plan(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val weeks: List<Week>,
)

val samplePlan = Plan(
    id = "1",
    name = "Poziom 3 Ogólny",
    weeks = (1..6).map {
        Week(
            id = "1",
            trainings = listOf(
                Training(
                    id = "1",
                    name = "TRENING 1 - PUSH 1",
                    exercises = listOf(
                        Exercise(
                            id = "1",
                            name = "Wyciskanie sztangi na ławce poziomej",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 4..6,
                            rest = "2-3min",
                        ),
                        Exercise(
                            id = "2",
                            name = "Rozpiętki hantlami na ławce skos dodatni",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            id = "3",
                            name = "Wyciskanie hantli nad głowę siedzac",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 8..10,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            id = "4",
                            name = "Wznosy hantli bokiem stojąc",
                            sets = Sets(standard = 1, drop = 3),
                            repsRange = 10..20,
                            rest = "30sek",
                        ),
                        Exercise(
                            id = "5",
                            name = "Prostowanie ramion na wyciągu",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek",
                        ),
                        Exercise(
                            id = "6",
                            name = "Wybierz dowolne ćwiczenie z playlisty z filmu",
                            sets = Sets(standard = 1, drop = 2),
                            repsRange = 10..20,
                            rest = "30sek",
                        )
                    )
                ),
                Training(
                    id = "2",
                    name = "TRENING 2 - PULL 1",
                    exercises = listOf(
                        Exercise(
                            id = "7",
                            name = "Wiosłowanie sztangą",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 6..8,
                            rest = "2-3min",
                        ),
                        Exercise(
                            id = "8",
                            name = "Sciąganie drążka wyciągu górnego chwytem V",
                            sets = Sets(standard = 3, drop = 0),
                            repsRange = 8..10,
                            rest = "2-3min"
                        ),
                        Exercise(
                            id = "9",
                            name = "Wiosłowanie hantlami w oparciu o ławkę",
                            sets = Sets(standard = 2, drop = 0),
                            repsRange = 10..12,
                            rest = "2-3min"
                        ),
                        Exercise(
                            id = "10",
                            name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                            sets = Sets(standard = 1, drop = 2),
                            repsRange = 10..20,
                            rest = "30sek"
                        ),
                        Exercise(
                            id = "11",
                            name = "Uginanie hantli nad modlitewniku",
                            sets = Sets(standard = 2, drop = 1),
                            repsRange = 10..12,
                            rest = "2-3min +30sek"
                        ),
                        Exercise(
                            id = "12",
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
