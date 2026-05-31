package com.kadawatcha.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kadawatcha.app.ui.MainScreen

class LoginViewModel : ViewModel() {

    private val db = Firebase.firestore
    // Les états de l'écran (ce que la vue va afficher)
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var usernameError by mutableStateOf(false)
    var emptyUser by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var emptyPassword by mutableStateOf(false)
    var loginSuccess by mutableStateOf(false)

    // La logique (le cerveau)
    fun onLoginClick() {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()

        // Reset des erreurs avant la tentative
        usernameError = false
        passwordError = false
        loginSuccess = false
        emptyUser = trimmedUsername.isEmpty()
        emptyPassword = trimmedPassword.isEmpty()

        if (emptyUser || emptyPassword) return

        db.collection("users")
            .whereEqualTo("username", trimmedUsername)
            .whereEqualTo("password", trimmedPassword)
            .get()
            .addOnSuccessListener { documents ->
                // Si la liste des documents retournés n'est pas vide, c'est qu'on a trouvé le bon utilisateur !
                if (!documents.isEmpty) {
                    loginSuccess = true
                    println("🔥 Connexion réussie pour $trimmedUsername !")

                    // On déclenche l'action de succès (par exemple, aller sur le MainScreen)
                    // MainScreen()
                } else {
                    // Aucun utilisateur ne correspond à cette combinaison
                    loginSuccess = false
                    usernameError = true
                    passwordError = true
                    println("🚨 Identifiants incorrects...")
                }
            }
            .addOnFailureListener { e ->
                loginSuccess = false
                println("🚨 Erreur Firestore : $e")
            }
    }
}