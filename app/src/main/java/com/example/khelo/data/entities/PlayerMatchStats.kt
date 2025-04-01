package com.example.khelo.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "player_match_stats",
    primaryKeys = ["matchId", "playerPhone"],
    foreignKeys = [
        ForeignKey(
            entity = Match::class,
            parentColumns = ["matchId"],
            childColumns = ["matchId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("playerPhone")]
)
data class PlayerMatchStats(
    val matchId: Long,
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
