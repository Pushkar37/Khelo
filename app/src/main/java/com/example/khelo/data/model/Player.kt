package com.example.khelo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String = "",
    val userPhone: String,
    val totalMatches: Int = 0,
    val totalRuns: Int = 0,
    val totalBallsFaced: Int = 0,
    val totalWickets: Int = 0,
    val totalCatches: Int = 0,
    val profilePhotoPath: String? = null
)
