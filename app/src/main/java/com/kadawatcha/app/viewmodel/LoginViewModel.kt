package com.kadawatcha.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // Les états de l'écran (ce que la vue va afficher)
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var usernameError by mutableStateOf(false)
    var emptyUser by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var emptyPassword by mutableStateOf(false)

    // La logique (le cerveau)
    fun onLoginClick() {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()

        // Reset des erreurs
        usernameError = false
        emptyUser = false
        passwordError = false
        emptyPassword = false

        when {
            trimmedUsername.isEmpty() -> emptyUser = true
            trimmedPassword.isEmpty() -> emptyPassword = true
            trimmedUsername == "User1" -> {
                if (trimmedPassword != "Pass2") {
                    passwordError = true
                } else {
                    // Succès ! On pourrait changer d'écran ici
                }
            }

            else -> usernameError = true
        }
    }
}