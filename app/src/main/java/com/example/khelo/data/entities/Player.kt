package com.example.khelo.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["phoneNumber"],
            childColumns = ["userPhone"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    val userPhone: String,
    val totalMatches: Int = 0,
    val totalRuns: Int = 0,
    val totalBallsFaced: Int = 0,
    val totalWickets: Int = 0,
    val totalCatches: Int = 0,
    val profilePhotoPath: String? = null
)
