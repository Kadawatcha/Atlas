package com.kadawatcha.app.model


data class User(
    val username: String = "",
    val password: String = "", // tempo ppour tests ( pas mdp en brut)
    val role: String = "Member Atlas"
)