package com.kadawatcha.atlas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kadawatcha.atlas.utils.SecurityUtils

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
    // Identifiant unique récupéré après la connexion (utilisé pour charger le profil)
    var userId by mutableStateOf("")

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

        if (emptyUser || emptyPassword) return 

        // On cherche par le champ "username". 
        // Compatibilité : fonctionne si l'ID du document est le pseudo (anciens) 
        // ou si c'est un ID auto-généré (nouveaux).
        db.collection("users")
            .whereEqualTo("username", trimmedUsername)
            .get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot.isEmpty) { // pas d'utilisateur trouvé avec ce pseudo
                    loginSuccess = false
                    correctUser = false
                    usernameError = true

                } else {
                    val document = querySnapshot.documents[0]
                    val realPassword = document.getString("password")
                    val hashedInputPassword = SecurityUtils.hashPassword(trimmedPassword)

                    if (realPassword == hashedInputPassword) {
                        // On récupère l'ID du document Firestore (notre clé unique stable)
                        userId = document.id
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