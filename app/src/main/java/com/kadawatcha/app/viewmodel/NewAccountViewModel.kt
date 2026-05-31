package com.kadawatcha.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kadawatcha.app.model.User

class NewAccountViewModel: ViewModel() {
    private val db = Firebase.firestore
    var username by mutableStateOf("")
    var usernameError by mutableStateOf(false)
    var usernameEmpty by mutableStateOf(false)
    var usernameAlreadyTaken by mutableStateOf(false)

    var password by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var passwordEmpty by mutableStateOf(false)

    var repeatPassword by mutableStateOf("")
    var repeatEmpty by mutableStateOf(false)
    var repeatBad by mutableStateOf(false)

    fun onCreateAccountClick() {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()
        val trimmedRepeatPassword = repeatPassword.trim()

        usernameEmpty = false
        usernameError = false
        usernameAlreadyTaken = false
        passwordError = false
        passwordEmpty = false
        repeatEmpty = false
        repeatBad = false

        when {
            trimmedUsername.isEmpty() -> usernameEmpty = true
            trimmedPassword.isEmpty() -> passwordEmpty = true
            trimmedPassword.length < 8 -> passwordError = true
            trimmedRepeatPassword.isEmpty() -> repeatEmpty = true
            trimmedPassword != trimmedRepeatPassword -> repeatBad = true
            else -> {
                createAccount()
            }
        }
    }

    fun createAccount(){

        val newUser = User(
            username = username,
            password = password
        )
        db.collection("users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                println("Bienvenue $username chez Atlas !\nVous pouvez vous connecter !")
                username = ""
                password = ""
                repeatPassword = ""
            }
            .addOnFailureListener { e ->
                println("🚨 Aïe, erreur : $e")
            }
    }

}