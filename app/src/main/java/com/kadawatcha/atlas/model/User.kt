package com.kadawatcha.atlas.model


data class User(
    val id: String = "", // Identifiant unique (soit l'ID auto-généré, soit le pseudo pour les anciens comptes)
    val username: String = "",
    val password: String = "", // tempo pour tests (ne pas stocker de mdp en clair en prod)
    val role: String = "Member Atlas"
)