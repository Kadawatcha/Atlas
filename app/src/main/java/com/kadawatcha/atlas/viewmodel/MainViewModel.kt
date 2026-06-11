package com.kadawatcha.atlas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kadawatcha.atlas.utils.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {

    /**
     * stateIn transforme un Flow "froid" (qui ne s'active que si on l'écoute) 
     * en StateFlow "chaud" (qui garde toujours la dernière valeur en mémoire).
     * 
     * - scope: lié au cycle de vie du ViewModel (s'arrête si l'écran est détruit).
     * - started: attend 5 secondes avant de s'arrêter si plus personne n'écoute (évite les redémarrages inutiles).
     * - initialValue: la valeur affichée le temps que le fichier soit lu sur le disque.
     */
    val isDarkTheme: StateFlow<Boolean> = settingsManager.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false 
        )

    fun toggleTheme() {
        // Comme toggleDarkMode est une fonction "suspend", on doit la lancer dans une coroutine.
        viewModelScope.launch {
            settingsManager.toggleDarkMode()
        }
    }
}

/**
 * La FACTORY : Par défaut, Android ne sait créer que des ViewModels sans arguments.
 * Comme on a besoin de lui donner le 'SettingsManager', on crée cette "recette de cuisine".
 */
val MainViewModelFactory = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        // On récupère l'application pour avoir accès au 'Context' nécessaire au SettingsManager
        val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
        return MainViewModel(SettingsManager(application)) as T
    }
}
