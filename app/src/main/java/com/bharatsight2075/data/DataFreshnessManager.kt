package com.bharatsight2075.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "data_freshness")

@Singleton
class DataFreshnessManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val DB_ENCRYPTED = booleanPreferencesKey("db_encrypted")
    }

    suspend fun isDbEncrypted(): Boolean {
        return context.dataStore.data.map { it[DB_ENCRYPTED] ?: false }.first()
    }

    suspend fun setDbEncrypted(encrypted: Boolean) {
        context.dataStore.edit { it[DB_ENCRYPTED] = encrypted }
    }

    fun getLastUpdated(dataset: String): Flow<Long> {
        val key = longPreferencesKey(dataset)
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: 0L
        }
    }

    suspend fun updateTimestamp(dataset: String) {
        val key = longPreferencesKey(dataset)
        context.dataStore.edit { preferences ->
            preferences[key] = System.currentTimeMillis()
        }
    }

    fun isStale(lastUpdated: Long, thresholdMillis: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > thresholdMillis
    }
}
