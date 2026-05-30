package com.bharatsight2075.network

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class AuthInterceptor(private val context: Context) : Interceptor {

    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Get auth token from DataStore (synchronously since intercept is not suspend)
        val authToken = runBlocking {
            context.dataStore.data.map { it[AUTH_TOKEN_KEY] }.first() ?: ""
        }
        
        val authorizedRequest = request.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()
        
        return chain.proceed(authorizedRequest)
    }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { it[AUTH_TOKEN_KEY] = token }
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { it.remove(AUTH_TOKEN_KEY) }
    }
}
