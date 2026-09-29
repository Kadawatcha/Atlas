package com.kada.atlas.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object Validator {

    /**
     * Vérifie si le format du nom d'utilisateur est valide :
     * - Au moins 3 caractères
     * - Pas d'espaces
     */
    fun isUsernameValid(username: String): Boolean {
        val trimmed = username.trim()
        return trimmed.length >= 3 && !username.contains(" ")
    }

    /**
     * Vérifie si le format du mot de passe est valide :
     * - Au moins 8 caractères
     * - Pas d'espaces
     */
    fun isPasswordValid(password: String): Boolean {
        val trimmed = password.trim()
        return trimmed.length >= 8 && !password.contains(" ")
    }

    /**
     * Vérifie si un nom d'utilisateur est déjà utilisé dans Firestore.
     * @param onResult Callback avec true si disponible, false si déjà pris
     */
    fun checkUsernameAvailability(
        db: FirebaseFirestore,
        username: String,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .whereEqualTo("username", username.trim())
            .get()
            .addOnSuccessListener { query ->
                onResult(query.isEmpty)
            }
            .addOnFailureListener { exception ->
                Log.e("Validator", "Erreur lors de la vérification de disponibilité du pseudo: ${exception.message}", exception)
                onResult(false) // Par sécurité, on considère indisponible en cas d'erreur
            }
    }
}

