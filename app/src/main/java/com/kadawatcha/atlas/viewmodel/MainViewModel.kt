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

    // On transforme le Flow du SettingsManager en StateFlow pour que l'UI puisse l'observer
    val isDarkTheme: StateFlow<Boolean> = settingsManager.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false // Valeur par défaut pendant le chargement initial
        )

    fun toggleTheme() {
        viewModelScope.launch {
            settingsManager.toggleDarkMode()
        }
    }
}

val MainViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        // On récupère le "context" depuis les extras pour créer le SettingsManager
        val context = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
        return MainViewModel(SettingsManager(context)) as T
    }
}