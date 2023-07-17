package com.maruchin.data.diet

import kotlinx.coroutines.flow.Flow

interface DietRepository {

    fun getAll(): Flow<List<Diet>>

    fun getById(id: String): Flow<Diet?>
}