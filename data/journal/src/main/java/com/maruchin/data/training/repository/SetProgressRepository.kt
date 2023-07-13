package com.maruchin.data.training.repository

interface SetProgressRepository {

    suspend fun updateWeight(setId: String, weight: Float?)

    suspend fun updateReps(setId: String, reps: Int?)
}