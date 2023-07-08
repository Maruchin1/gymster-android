package com.maruchin.feature.traininglogs.editexercise

import com.maruchin.core.model.ID
import com.maruchin.data.training.model.JournalExercise
import com.maruchin.data.training.model.JournalDay

internal data class EditExerciseUiState(
    val name: String = "",
    val journalExercises: List<JournalExercise> = emptyList(),
    val selectedExerciseId: ID = ID.empty,
    val loading: Boolean = true,
) {

    val selectedExerciseIndex: Int
        get() = journalExercises.indexOfFirst { it.id == selectedExerciseId }

    constructor(journalDay: JournalDay?, selectedExerciseId: ID) : this(
        name = journalDay?.name.orEmpty(),
        journalExercises = journalDay?.exercises.orEmpty(),
        selectedExerciseId = selectedExerciseId,
        loading = false,
    )
}
