package com.example.khelo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerMatchStats(
    val matchId: String,
    val playerPhone: String,
    val team: String, // team1 or team2
    val runsScored: Int = 0,
    val ballsFaced: Int = 0,
    val wicketsTaken: Int = 0,
    val catches: Int = 0,
    val ballsBowled: Int = 0,
    val runsConceded: Int = 0,
    val isOut: Boolean = false,
    val outMethod: String? = null // caught, bowled, lbw, etc.
)
