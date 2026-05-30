package com.bharatsight2075.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "model_prefs")

@Singleton
class ModelVersionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val MODEL_VERSION_KEY = stringPreferencesKey("model_version")
    private val MODEL_DOWNLOAD_URL_KEY = stringPreferencesKey("model_download_url")

    /**
     * Check if a model update is required by comparing local version with remote version
     * In a real implementation, this would compare with the version from the API
     * For now, we'll return true if no version is stored (first run)
     */
    suspend fun isUpdateRequired(): Boolean {
        val storedVersion = context.dataStore.data.map { it[MODEL_VERSION_KEY] }.first()
        return storedVersion.isNullOrEmpty()
    }

    /**
     * Mark the current version as the latest we have
     */
    suspend fun markVersionAsCurrent(version: String) {
        context.dataStore.edit { it[MODEL_VERSION_KEY] = version }
    }

    /**
     * Get the currently stored model version
     */
    suspend fun getCurrentVersion(): String? {
        return context.dataStore.data.map { it[MODEL_VERSION_KEY] }.first()
    }

    /**
     * Store the download URL for the latest model
     */
    suspend fun storeDownloadUrl(url: String) {
        context.dataStore.edit { it[MODEL_DOWNLOAD_URL_KEY] = url }
    }

    /**
     * Get the stored download URL
     */
    suspend fun getDownloadUrl(): String? {
        return context.dataStore.data.map { it[MODEL_DOWNLOAD_URL_KEY] }.first()
    }

    /**
     * Clear stored model info (useful for testing or reset)
     */
    suspend fun clearModelInfo() {
        context.dataStore.edit {
            it.remove(MODEL_VERSION_KEY)
            it.remove(MODEL_DOWNLOAD_URL_KEY)
        }
    }
}
