package com.maruchin.data.training.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "plan_data_store")
private val activePlanId = stringPreferencesKey(name = "active_plan_id")

internal class JournalDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getActivePlanId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[activePlanId]
        }
    }

    suspend fun setActivePlanId(planId: String) {
        context.dataStore.edit { preferences ->
            preferences[activePlanId] = planId
        }
    }
}