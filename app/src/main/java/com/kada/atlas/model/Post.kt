package com.kada.atlas.model

data class Post(
    val id: String = java.util.UUID.randomUUID().toString(),
    val author: String,
    val content: String,
    val timestamp: String = "À l'instant"
)