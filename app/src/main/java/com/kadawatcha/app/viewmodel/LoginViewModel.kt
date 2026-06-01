package com.kadawatcha.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

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
    var correctUser by mutableStateOf(false)

    // La logique (le cerveau)
    fun onLoginClick() {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()

        // Reset des erreurs avant la tentative
        usernameError = false
        passwordError = false
        correctUser = false
        loginSuccess = false
        emptyUser = trimmedUsername.isEmpty()
        emptyPassword = trimmedPassword.isEmpty()

        if (emptyUser || emptyPassword) return // mdp et password vide (2eme verif)

        db.collection("users")
            .whereEqualTo("username", trimmedUsername)
            .get()
            .addOnSuccessListener { document ->

                if (document.isEmpty) { // pas d'utilisateur
                    loginSuccess = false
                    correctUser = false
                    usernameError = true

                } else {
                    val userDocument = document.documents[0]
                    val realPassword = userDocument.getString("password")

                    if (realPassword == trimmedPassword) {
                        loginSuccess = true
                    } else {
                        correctUser = true
                        passwordError = true
                    }

                }

            }
            .addOnFailureListener {
                loginSuccess = false
                // On sait jamais : \
            }
    }
}