package com.kadawatcha.atlas.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "theme")

class SettingsManager(private val context: Context) {

    // Dans SettingsManager.kt

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences()) // Si erreur de lecture, on renvoie du vide
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Ici 'false' est ta valeur par défaut si rien n'est enregistré
            preferences[DARK_MODE_KEY] ?: false
        }


    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    suspend fun toggleDarkMode() {
        context.dataStore.edit { preferences ->
            val current = preferences[DARK_MODE_KEY] ?: false
            preferences[DARK_MODE_KEY] = !current
        }
    }

}