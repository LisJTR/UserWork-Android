package com.torre.b2c2c_tfg.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Crea DataStore
private val Context.dataStore by preferencesDataStore(name = "theme_preferences")

class ThemePreferences(private val context: Context) {

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_user_")
    }

    suspend fun saveTheme(userId: Long, isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("${DARK_THEME_KEY.name}$userId")] = isDark
        }
    }

    suspend fun getTheme(userId: Long): Boolean {
        val key = booleanPreferencesKey("${DARK_THEME_KEY.name}$userId")
        return context.dataStore.data
            .map { prefs -> prefs[key] ?: false } // false = tema claro por defecto
            .first()
    }
}