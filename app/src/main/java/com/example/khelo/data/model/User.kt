package com.example.khelo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val phoneNumber: String,
    val name: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Long = System.currentTimeMillis()
)
