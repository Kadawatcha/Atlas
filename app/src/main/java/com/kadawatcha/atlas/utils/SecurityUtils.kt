package com.kadawatcha.atlas.utils

import java.security.MessageDigest

object SecurityUtils {

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        // Transforme le résultat mathématique en une chaîne de texte lisible
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}