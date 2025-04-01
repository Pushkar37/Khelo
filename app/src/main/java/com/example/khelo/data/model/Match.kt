package com.example.khelo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val matchId: String = "",
    val team1Name: String,
    val team2Name: String,
    val team1Captain: String,
    val team1ViceCaptain: String,
    val team2Captain: String,
    val team2ViceCaptain: String,
    val tossWinner: String,
    val tossDecision: String,
    val date: Long = System.currentTimeMillis(),
    val status: String = "ongoing", // ongoing, completed
    val createdByUserPhone: String,
    val team1Score: Int = 0,
    val team1Wickets: Int = 0,
    val team2Score: Int = 0,
    val team2Wickets: Int = 0,
    val currentInnings: Int = 1, // 1 or 2
    val overs: Float = 0f
)
