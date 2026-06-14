package com.kadawatcha.atlas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class ProfileViewModel : ViewModel() {
    private val db = Firebase.firestore

    var username by mutableStateOf("")
    
    // On utilise cet ID unique pour toutes les opérations Firestore
    // C'est plus fiable que le pseudo qui pourrait changer
    var userId by mutableStateOf("")

    /**
     * Charge le profil à partir de l'ID unique (récupéré au login)
     */
    fun loadUserProfile(id: String) {
        this.userId = id
        db.collection("users").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    this.username = document.getString("username") ?: ""
                }
            }
    }

    /**
     * Sauvegarde les modifications en utilisant l'ID unique comme référence
     */
    fun saveUserProfile() {
        if (userId.isBlank()) return
        
        // On utilise update pour ne modifier que le pseudo sans écraser le reste du document
        db.collection("users").document(userId)
            .update("username", username)
    }

}
