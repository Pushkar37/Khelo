package com.example.khelo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun ProvideScoringScreenState(
    team1Players: List<String>,
    team2Players: List<String>,
    team1NameSafe: String,
    team2NameSafe: String,
    tossWinner: String?,
    tossDecision: String?,
    team1Captain: String?,
    team1ViceCaptain: String?,
    team2Captain: String?,
    team2ViceCaptain: String?,
    initialStriker: String?,
    initialNonStriker: String?,
    initialBowler: String?,
    initialBattingTeamName: String,
    initialBowlingTeamName: String,
    initialBattedPlayers: List<String>,
    totalOvers: Int = 20,
    content: @Composable () -> Unit
) {
    // Create a ScoringScreenState instance
    val scoringScreenState = remember {
        ScoringScreenState(
            team1Players = team1Players,
            team2Players = team2Players,
            team1NameSafe = team1NameSafe,
            team2NameSafe = team2NameSafe,
            tossWinner = tossWinner,
            tossDecision = tossDecision,
            team1Captain = team1Captain ?: "",
            team1ViceCaptain = team1ViceCaptain ?: "",
            team2Captain = team2Captain ?: "",
            team2ViceCaptain = team2ViceCaptain ?: "",
            initialStriker = initialStriker ?: "",
            initialNonStriker = initialNonStriker ?: "",
            initialBowler = initialBowler ?: "",
            initialBattingTeamName = initialBattingTeamName,
            initialBowlingTeamName = initialBowlingTeamName,
            initialBattedPlayers = initialBattedPlayers,
            totalOvers = totalOvers
        )
    }
    
    // Provide the ScoringScreenState to the composition
    CompositionLocalProvider(LocalScoringScreenState provides scoringScreenState) {
        content()
    }
}
