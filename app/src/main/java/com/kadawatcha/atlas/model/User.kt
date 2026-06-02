package com.kadawatcha.atlas.model


data class User(
    val username: String = "",
    val password: String = "", // tempo ppour tests ( pas mdp en brut)
    val role: String = "Member Atlas"
)