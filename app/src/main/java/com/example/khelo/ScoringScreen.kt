package com.example.khelo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.khelo.LocalScoringScreenState
import com.example.khelo.ScoringScreenState

// Define the PrimaryGreen color is now in ScoringScreenState.kt
// val PrimaryGreen = Color(0xFF1B5E20)

// ScoringScreenState class has been moved to ScoringScreenState.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoringScreen(
    navController: NavController,
    team1Name: String? = null,
    team2Name: String? = null,
    team1Players: List<String> = emptyList(),
    team2Players: List<String> = emptyList(),
    tossWinner: String? = null,
    tossDecision: String? = null,
    team1Captain: String? = null,
    team1ViceCaptain: String? = null,
    team2Captain: String? = null,
    team2ViceCaptain: String? = null,
    striker: String? = null,
    nonStriker: String? = null,
    bowler: String? = null,
    wicketkeeper: String? = null,
    battingTeam: String? = null,
    bowlingTeam: String? = null,
    totalOvers: Int = 20
) {
    // Safe defaults
    val team1NameSafe = team1Name ?: "Team 1"
    val team2NameSafe = team2Name ?: "Team 2"
    val battingTeamName = battingTeam ?: team1NameSafe
    val bowlingTeamName = bowlingTeam ?: team2NameSafe
    
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
            initialStriker = striker ?: "",
            initialNonStriker = nonStriker ?: "",
            initialBowler = bowler ?: "",
            initialBattingTeamName = battingTeamName,
            initialBowlingTeamName = bowlingTeamName,
            initialBattedPlayers = listOfNotNull(striker, nonStriker).filter { it.isNotEmpty() },
            totalOvers = totalOvers
        )
    }

    var maxBalls by rememberSaveable { mutableIntStateOf(6) }
    
    // Provide the ScoringScreenState to the CompositionLocal
    CompositionLocalProvider(LocalScoringScreenState provides scoringScreenState) {
    
    // Use the ScoringScreenState instance
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$battingTeamName v/s $bowlingTeamName") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Implement check mark action */ }) {
                        Icon(Icons.Default.Check, contentDescription = "Complete")
                    }
                    IconButton(onClick = { /* TODO: Implement camera action */ }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Camera"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            // Mini Scorecard
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = scoringScreenState.battingTeamName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = "${scoringScreenState.totalRuns}/${scoringScreenState.totalWickets}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Overs: ${scoringScreenState.completedOvers}.${scoringScreenState.currentBalls}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        Text(
                            text = "RR: ${scoringScreenState.getFormattedRunRate()}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Show target in second innings
                    if (scoringScreenState.currentInnings == 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Target: ${scoringScreenState.target}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            
                            Text(
                                text = "Req RR: ${scoringScreenState.getFormattedRequiredRunRate()}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    } else {
                        // Show projected score in first innings
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Projected: ${scoringScreenState.getProjectedScore()}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }
            }
            
            // Batsmen and Bowler Stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Header row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Batsman",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "R",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "B",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "4s",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "6s",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "SR",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    
                    // Striker row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${scoringScreenState.striker}*",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "${scoringScreenState.strikerRuns}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${scoringScreenState.strikerBalls}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.strikerFours}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.strikerSixes}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (scoringScreenState.strikerBalls > 0) 
                                    String.format("%.1f", scoringScreenState.strikerRuns.toFloat() / scoringScreenState.strikerBalls * 100)
                                  else "0.0",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    // Non-striker row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = scoringScreenState.nonStriker,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            text = "${scoringScreenState.nonStrikerRuns}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.nonStrikerBalls}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.nonStrikerFours}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.nonStrikerSixes}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (scoringScreenState.nonStrikerBalls > 0) 
                                String.format("%.1f", scoringScreenState.nonStrikerRuns.toFloat() / scoringScreenState.nonStrikerBalls * 100)
                              else "0.0",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    
                    // Bowler row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Bowler",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "O",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "M",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "R",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "W",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "ER",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = scoringScreenState.currentBowler,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${scoringScreenState.bowlerOvers}.${scoringScreenState.currentOverBalls}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.bowlerMaidens}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.bowlerRuns}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${scoringScreenState.bowlerWickets}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (scoringScreenState.bowlerOvers > 0 || scoringScreenState.currentOverBalls > 0) {
                                val balls = scoringScreenState.bowlerOvers * 6 + scoringScreenState.currentOverBalls
                                String.format("%.2f", scoringScreenState.bowlerRuns.toFloat() * 6 / balls)
                            } else "0.00",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // Current Over Display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "This over:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Display current over events
                        scoringScreenState.currentOverEvents.forEachIndexed { index, event ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            event == "W" -> Color.Red
                                            event == "Wd" || event == "Nb" -> Color.Gray// Orange
                                            event == "0" -> Color.White
                                            event.contains("b") || event.contains("lb") -> Color.LightGray // Green
                                            event.toIntOrNull() == 4 -> Color.Cyan
                                            event.toIntOrNull() == 6 -> Color.Magenta // Green
                                            else -> Color(0xFF4CAF50) // Green for runs
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (event == "Wd" || event == "Nb"){
                                    maxBalls++
                                }
                                Text(
                                    text = event,
                                    color = if (event == "0") Color.Black else Color.White,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
                        // Add placeholder for remaining balls in over
                        repeat(maxBalls - scoringScreenState.currentOverEvents.size) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray.copy(alpha = 0.3f))
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            
            // Extras Checkboxes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = scoringScreenState.isWide,
                        onCheckedChange = {
                            scoringScreenState.isWide = it
                            if (it) {
                                scoringScreenState.isNoBall = false
                            }
                        }
                    )
                    Text("Wide")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = scoringScreenState.isNoBall,
                        onCheckedChange = {
                            scoringScreenState.isNoBall = it
                            if (it) {
                                scoringScreenState.isWide = false
                            }
                        }
                    )
                    Text("No Ball")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = scoringScreenState.isBye,
                        onCheckedChange = {
                            scoringScreenState.isBye = it
                            if (it) {
                                scoringScreenState.isLegBye = false
                            }
                        }
                    )
                    Text("Byes")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = scoringScreenState.isLegBye,
                        onCheckedChange = {
                            scoringScreenState.isLegBye = it
                            if (it) {
                                scoringScreenState.isBye = false
                            }
                        }
                    )
                    Text("Leg Byes")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = scoringScreenState.isWicket,
                        onCheckedChange = {
                            scoringScreenState.isWicket = it
                            if (it) {
                                // Show wicket type dialog when wicket is checked
                                scoringScreenState.showWicketTypeDialog = true
                            } else {
                                // Reset wicket type flags when wicket is unchecked
                                scoringScreenState.isRunOut = false
                                scoringScreenState.isBowled = false
                                scoringScreenState.isCaught = false
                                scoringScreenState.isStumped = false
                                scoringScreenState.isLBW = false
                                scoringScreenState.isHitWicket = false
                                scoringScreenState.fielderName = ""
                            }
                        }
                    )
                    Text("Wicket")
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Retire button
                OutlinedButton(
                    onClick = {
                        // Show dialog to select which batsman to retire
                        scoringScreenState.showRetireBatsmanDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1B5E20)
                    )
                ) {
                    Text("Retire")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Swap batsmen button
                OutlinedButton(
                    onClick = {
                        // Swap striker and non-striker
                        val temp = scoringScreenState.striker
                        scoringScreenState.striker = scoringScreenState.nonStriker
                        scoringScreenState.nonStriker = temp

                        val tempRuns = scoringScreenState.strikerRuns
                        scoringScreenState.strikerRuns = scoringScreenState.nonStrikerRuns
                        scoringScreenState.nonStrikerRuns = tempRuns

                        val tempBalls = scoringScreenState.strikerBalls
                        scoringScreenState.strikerBalls = scoringScreenState.nonStrikerBalls
                        scoringScreenState.nonStrikerBalls = tempBalls

                        val tempFours = scoringScreenState.strikerFours
                        scoringScreenState.strikerFours = scoringScreenState.nonStrikerFours
                        scoringScreenState.nonStrikerFours = tempFours

                        val tempSixes = scoringScreenState.strikerSixes
                        scoringScreenState.strikerSixes = scoringScreenState.nonStrikerSixes
                        scoringScreenState.nonStrikerSixes = tempSixes
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1B5E20)
                    )
                ) {
                    Text("Swap Batsman")
                }

            }
            
            // Wicket type dialog
            if (scoringScreenState.showWicketTypeDialog) {
                AlertDialog(
                    onDismissRequest = { scoringScreenState.showWicketTypeDialog = false },
                    title = { Text("How did the wicket fall?") },
                    text = {
                        Column {
                            // Bowled option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = true
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isBowled,
                                    onClick = {
                                        scoringScreenState.isBowled = true
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                )
                                Text(
                                    text = "Bowled",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // Caught option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = true
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isCaught,
                                    onClick = {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = true
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                )
                                Text(
                                    text = "Caught",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // LBW option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = true
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isLBW,
                                    onClick = {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = true
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                )
                                Text(
                                    text = "LBW",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // Stumped option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = true
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isStumped,
                                    onClick = {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = true
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = false
                                    }
                                )
                                Text(
                                    text = "Stumped",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // Hit wicket option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = true
                                        scoringScreenState.isRunOut = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isHitWicket,
                                    onClick = {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = true
                                        scoringScreenState.isRunOut = false
                                    }
                                )
                                Text(
                                    text = "Hit Wicket",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // Run out option
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = true
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isRunOut,
                                    onClick = {
                                        scoringScreenState.isBowled = false
                                        scoringScreenState.isCaught = false
                                        scoringScreenState.isStumped = false
                                        scoringScreenState.isLBW = false
                                        scoringScreenState.isHitWicket = false
                                        scoringScreenState.isRunOut = true
                                    }
                                )
                                Text(
                                    text = "Run Out",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            // Fielder input for caught, stumped, and run out
                            if (scoringScreenState.isCaught || scoringScreenState.isStumped || scoringScreenState.isRunOut) {
                                OutlinedTextField(
                                    value = scoringScreenState.fielderName,
                                    onValueChange = { scoringScreenState.fielderName = it },
                                    label = { Text("Fielder Name") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Close the dialog
                                scoringScreenState.showWicketTypeDialog = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                // Reset wicket flags and close the dialog
                                scoringScreenState.isWicket = false
                                scoringScreenState.isBowled = false
                                scoringScreenState.isCaught = false
                                scoringScreenState.isStumped = false
                                scoringScreenState.isLBW = false
                                scoringScreenState.isHitWicket = false
                                scoringScreenState.isRunOut = false
                                scoringScreenState.fielderName = ""
                                scoringScreenState.showWicketTypeDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            
            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Undo button
                Button(
                    onClick = {
                        // Implement undo functionality
                        val undoSuccessful = scoringScreenState.undoLastBall()
                        if (!undoSuccessful) {
                            // Could show a toast or message that there's nothing to undo
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1B5E20))
                ) {
                    Text("Undo")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Scorecard button (replacing Partnerships)
                Button(
                    onClick = {
                        scoringScreenState.showScorecardDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1B5E20))
                ) {
                    Text("Scorecard")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Extras button
                Button(
                    onClick = {
                        // TODO: Implement extras view
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1B5E20))
                ) {
                    Text("Extras")
                }
            }
            
            // Run buttons grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    runButtons(listOf(0, 1, 2, 3), scoringScreenState)
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    runButtons(listOf(4, 5, 6), scoringScreenState)
                    
                    // More button
                    OutlinedButton(
                        onClick = {
                            // TODO: Show dialog for more runs (7+)
                        },
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, Color(0xFF1B5E20)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF1B5E20)
                        )
                    ) {
                        Text("...")
                    }
                }
            }
            
            // New batsman dialog
            if (scoringScreenState.showNewBatsmanDialog) {
                AlertDialog(
                    onDismissRequest = { /* Dialog cannot be dismissed without selection */ },
                    title = { Text("Select New Batsman") },
                    text = {
                        Column {
                            val availableBatsmen = if (scoringScreenState.battingTeamName == team1NameSafe) {
                                team1Players
                            } else {
                                team2Players
                            }.filter { player ->
                                player != scoringScreenState.striker && 
                                player != scoringScreenState.nonStriker && 
                                !scoringScreenState.battedPlayers.contains(player)
                            }
                            
                            availableBatsmen.forEach { player ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scoringScreenState.newBatsmanName = player
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = scoringScreenState.newBatsmanName == player,
                                        onClick = { scoringScreenState.newBatsmanName = player }
                                    )
                                    Text(
                                        text = player,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Update striker with new batsman
                                scoringScreenState.striker = scoringScreenState.newBatsmanName
                                scoringScreenState.strikerRuns = 0
                                scoringScreenState.strikerBalls = 0
                                scoringScreenState.strikerFours = 0
                                scoringScreenState.strikerSixes = 0
                                
                                // Add to batted players list
                                scoringScreenState.battedPlayers = scoringScreenState.battedPlayers + scoringScreenState.newBatsmanName
                                
                                // Reset dialog state
                                scoringScreenState.showNewBatsmanDialog = false
                                scoringScreenState.newBatsmanName = ""
                            },
                            enabled = scoringScreenState.newBatsmanName.isNotEmpty()
                        ) {
                            Text("Confirm")
                        }
                    }
                )
            }
            
            // New bowler dialog
            if (scoringScreenState.showNewBowlerDialog) {
                AlertDialog(
                    onDismissRequest = { /* Dialog cannot be dismissed without selection */ },
                    title = { Text("Select New Bowler") },
                    text = {
                        Column {
                            val availableBowlers = if (scoringScreenState.bowlingTeamName == team1NameSafe) {
                                team1Players
                            } else {
                                team2Players
                            }.filter { player ->
                                player != scoringScreenState.lastBowler
                            }
                            
                            availableBowlers.forEach { player ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scoringScreenState.newBowlerName = player
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = scoringScreenState.newBowlerName == player,
                                        onClick = { scoringScreenState.newBowlerName = player }
                                    )
                                    Text(
                                        text = player,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Update current bowler
                                scoringScreenState.currentBowler = scoringScreenState.newBowlerName
                                scoringScreenState.bowlerOvers = 0
                                scoringScreenState.bowlerMaidens = 0
                                scoringScreenState.bowlerRuns = 0
                                scoringScreenState.bowlerWickets = 0
                                
                                // Reset dialog state
                                scoringScreenState.showNewBowlerDialog = false
                                scoringScreenState.newBowlerName = ""
                            },
                            enabled = scoringScreenState.newBowlerName.isNotEmpty()
                        ) {
                            Text("Confirm")
                        }
                    }
                )
            }
            
            // Retire batsman dialog
            if (scoringScreenState.showRetireBatsmanDialog) {
                AlertDialog(
                    onDismissRequest = { scoringScreenState.showRetireBatsmanDialog = false },
                    title = { Text("Select Batsman to Retire") },
                    text = {
                        Column {
                            val availableBatsmen = listOf(scoringScreenState.striker, scoringScreenState.nonStriker)
                            
                            availableBatsmen.forEach { player ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scoringScreenState.retireBatsmanName = player
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = scoringScreenState.retireBatsmanName == player,
                                        onClick = { scoringScreenState.retireBatsmanName = player }
                                    )
                                    Text(
                                        text = player,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Show retire type dialog
                                scoringScreenState.showRetireTypeDialog = true
                                scoringScreenState.showRetireBatsmanDialog = false
                            },
                            enabled = scoringScreenState.retireBatsmanName.isNotEmpty()
                        ) {
                            Text("Next")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                scoringScreenState.showRetireBatsmanDialog = false
                                scoringScreenState.retireBatsmanName = ""
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            
            // Retire type dialog
            if (scoringScreenState.showRetireTypeDialog) {
                AlertDialog(
                    onDismissRequest = { scoringScreenState.showRetireTypeDialog = false },
                    title = { Text("Select Retire Type") },
                    text = {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isRetiredHurt = true
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = scoringScreenState.isRetiredHurt,
                                    onClick = { scoringScreenState.isRetiredHurt = true }
                                )
                                Text(
                                    text = "Retired Hurt (can bat again later)",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scoringScreenState.isRetiredHurt = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = !scoringScreenState.isRetiredHurt,
                                    onClick = { scoringScreenState.isRetiredHurt = false }
                                )
                                Text(
                                    text = "Retired Out (cannot bat again)",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Handle retirement based on type
                                val retiredBatsman = scoringScreenState.retireBatsmanName
                                val isStriker = retiredBatsman == scoringScreenState.striker
                                
                                // Show dialog to select new batsman
                                scoringScreenState.showNewBatsmanDialog = true
                                
                                // Add to appropriate retired list
                                if (scoringScreenState.isRetiredHurt) {
                                    scoringScreenState.retiredHurtBatsmen = scoringScreenState.retiredHurtBatsmen + retiredBatsman
                                } else {
                                    scoringScreenState.retiredOutBatsmen = scoringScreenState.retiredOutBatsmen + retiredBatsman
                                    // Count as a wicket for retired out, but not for the bowler
                                    if (!scoringScreenState.isRetiredHurt) {
                                        scoringScreenState.totalWickets++
                                    }
                                }
                                
                                // Reset dialog state
                                scoringScreenState.showRetireTypeDialog = false
                                scoringScreenState.isRetiredHurt = true // Default to retired hurt for next time
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                scoringScreenState.showRetireTypeDialog = false
                                scoringScreenState.retireBatsmanName = ""
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            
            // Innings complete dialog
            if (scoringScreenState.showInningsCompleteDialog) {
                AlertDialog(
                    onDismissRequest = { /* Dialog cannot be dismissed without action */ },
                    title = { Text("Innings Complete") },
                    text = {
                        Column {
                            Text("First innings complete!")
                            Text("${scoringScreenState.battingTeamName} scored ${scoringScreenState.totalRuns}/${scoringScreenState.totalWickets} in ${scoringScreenState.completedOvers}.${scoringScreenState.currentBalls} overs")
                            Text("Target: ${scoringScreenState.totalRuns + 1} runs")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Please select opening batsmen and bowler for the second innings")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Start second innings
                                scoringScreenState.startSecondInnings()
                                
                                // Reset dialog state
                                scoringScreenState.showInningsCompleteDialog = false
                                
                                // Show new batsman and bowler dialogs
                                scoringScreenState.showNewBatsmanDialog = true
                            }
                        ) {
                            Text("Start Second Innings")
                        }
                    }
                )
            }
            
            // Match complete dialog
            if (scoringScreenState.showMatchCompleteDialog) {
                AlertDialog(
                    onDismissRequest = { /* Dialog cannot be dismissed without action */ },
                    title = { Text("Match Complete") },
                    text = {
                        Column {
                            val firstInningsTeam = if (scoringScreenState.currentInnings == 2) scoringScreenState.bowlingTeamName else scoringScreenState.battingTeamName
                            val secondInningsTeam = if (scoringScreenState.currentInnings == 2) scoringScreenState.battingTeamName else scoringScreenState.bowlingTeamName
                            
                            val winningTeam: String
                            val winMargin: String
                            
                            if (scoringScreenState.currentInnings == 2) {
                                if (scoringScreenState.totalRuns >= scoringScreenState.target) {
                                    // Batting team won
                                    winningTeam = scoringScreenState.battingTeamName
                                    winMargin = "${10 - scoringScreenState.totalWickets} wickets"
                                } else {
                                    // Bowling team won
                                    winningTeam = scoringScreenState.bowlingTeamName
                                    winMargin = "${scoringScreenState.target - scoringScreenState.totalRuns - 1} runs"
                                }
                            } else {
                                // First innings team won (unlikely scenario)
                                winningTeam = firstInningsTeam
                                winMargin = "by innings"
                            }
                            
                            Text("Match complete!")
                            Text("$firstInningsTeam: ${scoringScreenState.target - 1}/${if (scoringScreenState.currentInnings == 2) 10 else scoringScreenState.totalWickets}")
                            Text("$secondInningsTeam: ${scoringScreenState.totalRuns}/${scoringScreenState.totalWickets}")
                            Text("$winningTeam won by $winMargin")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Return to main screen
                                navController.navigate("MainScreen") {
                                    popUpTo("MainScreen") { inclusive = true }
                                }
                            }
                        ) {
                            Text("Back to Home")
                        }
                    }
                )
            }
            
            // Scorecard dialog
            if (scoringScreenState.showScorecardDialog) {
                AlertDialog(
                    onDismissRequest = { scoringScreenState.showScorecardDialog = false },
                    title = { Text("Scorecard") },
                    text = {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp)
                        ) {
                            // Match summary
                            Text(
                                text = "${scoringScreenState.battingTeamName} vs ${scoringScreenState.bowlingTeamName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Innings ${scoringScreenState.currentInnings}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "${scoringScreenState.battingTeamName}: ${scoringScreenState.totalRuns}/${scoringScreenState.totalWickets} (${scoringScreenState.completedOvers}.${scoringScreenState.currentOverBalls})",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Batsmen header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Batsman",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    text = "R",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "B",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "4s",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "6s",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "SR",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                            }
                            
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            
                            // Current batsmen
                            if (scoringScreenState.striker.isNotEmpty()) {
                                BatsmanScoreRow(
                                    name = scoringScreenState.striker + " *",
                                    runs = scoringScreenState.strikerRuns,
                                    balls = scoringScreenState.strikerBalls,
                                    fours = scoringScreenState.strikerFours,
                                    sixes = scoringScreenState.strikerSixes
                                )
                            }
                            
                            if (scoringScreenState.nonStriker.isNotEmpty()) {
                                BatsmanScoreRow(
                                    name = scoringScreenState.nonStriker,
                                    runs = scoringScreenState.nonStrikerRuns,
                                    balls = scoringScreenState.nonStrikerBalls,
                                    fours = scoringScreenState.nonStrikerFours,
                                    sixes = scoringScreenState.nonStrikerSixes
                                )
                            }
                            
                            // All other batsmen who have batted
                            scoringScreenState.battedPlayers.forEach { playerName ->
                                if (playerName != scoringScreenState.striker && 
                                    playerName != scoringScreenState.nonStriker) {
                                    // Get player stats from the state
                                    val playerStats = scoringScreenState.getBatsmanStats(playerName)
                                    BatsmanScoreRow(
                                        name = playerName,
                                        runs = playerStats.runs,
                                        balls = playerStats.balls,
                                        fours = playerStats.fours,
                                        sixes = playerStats.sixes
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Extras and total
                            Text(
                                text = "Extras: ${scoringScreenState.totalExtras} (Wd: ${scoringScreenState.totalWides}, Nb: ${scoringScreenState.totalNoBalls}, B: ${scoringScreenState.totalByes}, Lb: ${scoringScreenState.totalLegByes})",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Bowlers header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Bowler",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    text = "O",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "M",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "R",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "W",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                                Text(
                                    text = "Econ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.7f)
                                )
                            }
                            
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            
                            // Current bowler
                            if (scoringScreenState.currentBowler.isNotEmpty()) {
                                BowlerScoreRow(
                                    name = scoringScreenState.currentBowler + " *",
                                    overs = scoringScreenState.bowlerOvers,
                                    maidens = scoringScreenState.bowlerMaidens,
                                    runs = scoringScreenState.bowlerRuns,
                                    wickets = scoringScreenState.bowlerWickets
                                )
                            }
                            
                            // All other bowlers who have bowled
                            scoringScreenState.bowlersList.forEach { bowlerName ->
                                if (bowlerName != scoringScreenState.currentBowler) {
                                    // Get bowler stats from the state
                                    val bowlerStats = scoringScreenState.getBowlerStats(bowlerName)
                                    BowlerScoreRow(
                                        name = bowlerName,
                                        overs = bowlerStats.overs,
                                        maidens = bowlerStats.maidens,
                                        runs = bowlerStats.runs,
                                        wickets = bowlerStats.wickets
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Fall of wickets
                            Text(
                                text = "Fall of Wickets:",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // TODO: Implement fall of wickets display
                            
                            if (scoringScreenState.currentInnings == 2) {
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                // Required runs and run rate
                                val requiredRuns = scoringScreenState.target - scoringScreenState.totalRuns
                                val remainingBalls = (scoringScreenState.totalOvers * 6) - 
                                                    (scoringScreenState.completedOvers * 6 + scoringScreenState.currentOverBalls)
                                val requiredRunRate = if (remainingBalls > 0) {
                                    (requiredRuns.toFloat() / remainingBalls) * 6
                                } else {
                                    0f
                                }
                                
                                Text(
                                    text = "Required: ${requiredRuns} runs from ${remainingBalls / 6}.${remainingBalls % 6} overs",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                
                                Text(
                                    text = "Required Run Rate: ${String.format("%.2f", requiredRunRate)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                
                                // Projected score
                                val currentRunRate = if (scoringScreenState.completedOvers > 0 || scoringScreenState.currentOverBalls > 0) {
                                    val totalBalls = scoringScreenState.completedOvers * 6 + scoringScreenState.currentOverBalls
                                    (scoringScreenState.totalRuns.toFloat() / totalBalls) * 6
                                } else {
                                    0f
                                }
                                
                                val projectedScore = scoringScreenState.totalRuns + 
                                                    (currentRunRate * ((scoringScreenState.totalOvers * 6) - 
                                                                      (scoringScreenState.completedOvers * 6 + scoringScreenState.currentOverBalls)) / 6).toInt()
                                
                                Text(
                                    text = "Projected Score: ${projectedScore}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { scoringScreenState.showScorecardDialog = false }
                        ) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
    }
}

// Batsman score row component
@Composable
fun BatsmanScoreRow(
    name: String,
    runs: Int,
    balls: Int,
    fours: Int,
    sixes: Int
) {
    val strikeRate = if (balls > 0) {
        (runs.toFloat() / balls) * 100
    } else {
        0f
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = runs.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = balls.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = fours.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = sixes.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = String.format("%.2f", strikeRate),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
    }
}

// Bowler score row component
@Composable
fun BowlerScoreRow(
    name: String,
    overs: Int,
    maidens: Int,
    runs: Int,
    wickets: Int
) {
    val balls = overs % 6
    val completedOvers = overs / 6
    val economy = if (overs > 0) {
        (runs.toFloat() / (completedOvers + (balls.toFloat() / 6)))
    } else {
        0f
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = "$completedOvers.${balls}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = maidens.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = runs.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = wickets.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
        Text(
            text = String.format("%.2f", economy),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
    }
}

@Composable
private fun RowScope.runButtons(runs: List<Int>, scoringScreenState: ScoringScreenState) {
    runs.forEach { run ->
        OutlinedButton(
            onClick = {
                if (scoringScreenState.isWicket) {
                    scoringScreenState.processWicket()
                } else {
                    scoringScreenState.processBallOutcome(run)
                }
            },
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, Color(0xFF1B5E20)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF1B5E20)
            )
        ) {
            Text("$run")
        }
    }
}
