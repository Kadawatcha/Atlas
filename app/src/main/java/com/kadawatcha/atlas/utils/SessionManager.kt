package com.kadawatcha.atlas.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

// On initialise le DataStore au niveau du fichier
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("logged_in_username")
    }

    // 1. Sauvegarder
    suspend fun saveSession(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    // 2. Lire (Flow permet de réagir en temps réel)
    val loggedInUser: Flow<String?> = context.dataStore.data.map { preferences ->
        delay(500.milliseconds)
        preferences[USERNAME_KEY]
    }

    // 3. Déconnecter
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USERNAME_KEY)
        }
    }
}