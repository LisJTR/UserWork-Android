package com.torre.b2c2c_tfg.ui.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SessionManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "user_session")

    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
    }

    suspend fun saveSession(userId: Long, userType: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
            prefs[USER_TYPE_KEY] = userType
        }
    }

    suspend fun getSession(): Pair<Long?, String?> {
        val prefs = context.dataStore.data.first()
        val id = prefs[USER_ID_KEY]
        val type = prefs[USER_TYPE_KEY]
        return Pair(id, type)
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}