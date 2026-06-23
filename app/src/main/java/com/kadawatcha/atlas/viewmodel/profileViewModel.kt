package com.kadawatcha.atlas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kadawatcha.atlas.utils.Validator


class profileViewModel : ViewModel() {
    private val db = Firebase.firestore

    var username by mutableStateOf("")
    private var initialUsername = ""

    var usernameAlreadyTaken by mutableStateOf(false)

    var hasChanged by mutableStateOf(false)

    // On utilise cet ID unique pour toutes les opérations Firestore
    // C'est plus fiable que le pseudo qui pourrait changer
    var userId by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    fun onUsernameChange(newValue: String) {
        username = newValue
        usernameAlreadyTaken = false // On cache l'erreur dès qu'on recommence à écrire
        hasChanged = username.trim() != initialUsername.trim()
    }

    /**
     * Charge le profil à partir de l'ID unique (récupéré au login)
     */
    fun loadUserProfile(id: String) {
        if (id.isBlank()) return
        this.userId = id
        isLoading = true
        db.collection("users").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fetchedUsername = document.getString("username") ?: ""
                    this.username = fetchedUsername
                    this.initialUsername = fetchedUsername
                    this.hasChanged = false
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    /**
     * Sauvegarde les modifications en utilisant l'ID unique comme référence
     */
    fun saveUserProfile() {
        if (userId.isBlank()) return

        usernameAlreadyTaken = false

        if (!Validator.isUsernameValid(username)) {
            // Ici on pourrait ajouter une gestion d'erreur spécifique au format si besoin
            return
        }

        isLoading = true
        // On utilise update pour ne modifier que les champs nécessaires sans écraser le reste du document
        db.collection("users").document(userId).get().addOnSuccessListener { document ->
            val oldUsername = document.getString("username") ?: ""

            if (username.trim() == oldUsername) {
                performSave()
            } else {
                Validator.checkUsernameAvailability(db, username) { isAvailable ->
                    if (isAvailable) {
                        performSave()
                    } else {
                        usernameAlreadyTaken = true
                        isLoading = false
                    }
                }
            }
        }
    }


    private fun performSave() {
        isLoading = true
        val savedUsername = username.trim()
        db.collection("users").document(userId)
            .update("username", savedUsername)
            .addOnCompleteListener {
                isLoading = false
                if (it.isSuccessful) {
                    initialUsername = savedUsername
                    hasChanged = false
                }
            }
    }
}
