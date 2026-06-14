package com.kadawatcha.atlas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kadawatcha.atlas.model.User
import com.kadawatcha.atlas.utils.SecurityUtils

class NewAccountViewModel : ViewModel() {
    private val db = Firebase.firestore
    var username by mutableStateOf("")
    var usernameError by mutableStateOf(false)
    var usernameEmpty by mutableStateOf(false)

    var usernameHadSpace by mutableStateOf(false)
    var usernameAlreadyTaken by mutableStateOf(false)

    var password by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var passwordEmpty by mutableStateOf(false)

    var passwordHadSpace by mutableStateOf(false)

    var repeatPassword by mutableStateOf("")
    var repeatEmpty by mutableStateOf(false)
    var repeatBad by mutableStateOf(false)
    var creationSuccess by mutableStateOf(false)

    fun onCreateAccountClick() {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()
        val trimmedRepeatPassword = repeatPassword.trim()

        usernameEmpty = false
        usernameError = false
        usernameAlreadyTaken = false
        usernameHadSpace = false
        passwordError = false
        passwordEmpty = false
        passwordHadSpace = false
        repeatEmpty = false
        repeatBad = false

        when {
            trimmedUsername.isEmpty() -> usernameEmpty = true
            trimmedUsername.contains(" ") -> usernameHadSpace = true
            trimmedPassword.isEmpty() -> passwordEmpty = true
            trimmedPassword.contains(" ") -> passwordHadSpace = true
            trimmedPassword.length < 8 -> passwordError = true
            trimmedRepeatPassword.isEmpty() -> repeatEmpty = true
            trimmedPassword != trimmedRepeatPassword -> repeatBad = true
            else -> checkUsernameAndCreate(
                trimmedUsername, trimmedPassword

            )
        }
    }

    fun checkUsernameAndCreate(trimmedUsername: String, trimmedPassword: String) {

        val hashedPassword = SecurityUtils.hashPassword(trimmedPassword)

        db.collection("users").whereEqualTo("username", trimmedUsername).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty()) {
                    // On crée une référence de document vide pour obtenir un ID auto-généré par Firestore
                    val newUserRef = db.collection("users").document()
                    val newUser = User(
                        id = newUserRef.id, // On stocke cet ID unique dans l'objet User
                        username = trimmedUsername,
                        password = hashedPassword
                    )
                    
                    // On enregistre les données dans Firestore en utilisant l'ID unique comme nom de document
                    newUserRef.set(newUser).addOnSuccessListener { _ ->
                        creationSuccess = true
                    }
                } else {
                    usernameAlreadyTaken = true
                }
            }.addOnFailureListener { e ->
                println("Error checking username: $e")
            }
    }
}