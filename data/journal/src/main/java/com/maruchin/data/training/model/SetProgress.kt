package com.maruchin.data.training.model

import java.util.UUID

data class SetProgress(
    val id: String = UUID.randomUUID().toString(),
    val weight: Float?,
    val reps: Int?,
) {

    companion object {

        fun createMultiple(numOfSets: Int): List<SetProgress> {
            return (1..numOfSets).map { SetProgress(weight = null, reps = null) }
        }
    }
}
