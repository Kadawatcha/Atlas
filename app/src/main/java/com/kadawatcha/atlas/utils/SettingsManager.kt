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

// On crée une instance unique de DataStore. 
// "by preferencesDataStore" est un "délégué" qui garantit que le fichier n'est ouvert qu'une seule fois.
private val Context.dataStore by preferencesDataStore(name = "theme_preferences")

class SettingsManager(private val context: Context) {

    companion object {
        // Une clé unique pour identifier notre donnée dans le fichier.
        // C'est comme le nom d'une colonne dans une base de données.
        private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    }

    /**
     * Un FLOW est un flux de données (un tuyau). 
     * Au lieu de donner la valeur une seule fois, il prévient l'app dès que la valeur change.
     */
    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            // Si le fichier est corrompu ou illisible, on renvoie des préférences vides pour éviter le crash.
            if (exception is IOException) {
                emit(emptyPreferences()) 
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // On récupère la valeur. Si elle est nulle (1er lancement), on renvoie 'false'.
            preferences[DARK_MODE_KEY] ?: false
        }

    /**
     * "suspend" signifie que cette fonction peut prendre du temps (écriture sur disque).
     * Elle doit obligatoirement être appelée depuis une Coroutine (un fil d'exécution secondaire).
     */
    suspend fun toggleDarkMode() {
        context.dataStore.edit { preferences ->
            val current = preferences[DARK_MODE_KEY] ?: false
            preferences[DARK_MODE_KEY] = !current
        }
    }
}
