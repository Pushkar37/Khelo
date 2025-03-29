package com.example.khelo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

@Composable
fun MatchScoringScreen(
    navController: NavHostController,
    team1Name: String,
    team2Name: String,
    tossWinner: String,
    tossDecision: String,
    openingBatsman1: String,
    openingBatsman2: String,
    openingBowler: String,
    wicketKeeper: String,
    battingTeam: String,
    bowlingTeam: String
) {
    var runs by remember { mutableStateOf(0) }
    var wickets by remember { mutableStateOf(0) }
    var overs by remember { mutableStateOf(0.0) }
    var totalOvers by remember { mutableStateOf(20.0) } // Default 20 overs
    var currentOverBalls by remember { mutableStateOf(0) }
    var currentBowlerOvers by remember { mutableStateOf(0.0) }
    
    // Batsmen states
    var currentBatsman1Runs by remember { mutableStateOf(0) }
    var currentBatsman1Balls by remember { mutableStateOf(0) }
    var currentBatsman1Fours by remember { mutableStateOf(0) }
    var currentBatsman1Sixes by remember { mutableStateOf(0) }
    
    // Bowler states
    var currentBowlerRuns by remember { mutableStateOf(0) }
    var currentBowlerWickets by remember { mutableStateOf(0) }
    
    // Current over events
    var currentOverEvents by remember { mutableStateOf(mutableListOf<String>()) }
    
    // Calculate current run rate
    val currentRunRate = if (overs > 0) (runs / overs).roundToInt() else 0
    
    // Calculate required run rate (only for batting second team)
    val requiredRunRate = if (battingTeam == team2Name && overs > 0) {
        val target = runs // Target is the first innings total
        ((target - runs) / (totalOvers - overs)).roundToInt()
    } else {
        0
    }
    
    // Calculate projected score (only for batting first team)
    val projectedScore = if (battingTeam == team1Name) {
        (currentRunRate * totalOvers).roundToInt()
    } else {
        0
    }

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        drawerContent = { DrawerPanel(navController, selectedItem = "Item 1", onItemClick = { }) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                    scope = rememberCoroutineScope()
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Match Info
                Text(
                    text = "$battingTeam vs $bowlingTeam",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Score Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Score: $runs/$wickets",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Overs: ${overs.roundToInt()}.${(overs % 1 * 10).toInt()} / $totalOvers",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Current Run Rate: $currentRunRate",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        if (battingTeam == team1Name) {
                            Text(
                                text = "Projected Score: $projectedScore",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = "Required Run Rate: $requiredRunRate",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }

                // Current Batsmen Scorecard
                Text(
                    text = "Batsmen",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Batsman 1 Scorecard
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = openingBatsman1,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Runs: $currentBatsman1Runs",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Balls: $currentBatsman1Balls",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "4s: $currentBatsman1Fours",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "6s: $currentBatsman1Sixes",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Strike Rate: ${(currentBatsman1Runs * 100.0 / currentBatsman1Balls).roundToInt()}%",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }

                // Bowler Scorecard
                Text(
                    text = "Bowler",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = openingBowler,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Overs: ${currentBowlerOvers.roundToInt()}.${(currentBowlerOvers % 1 * 10).toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Runs: $currentBowlerRuns",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Wickets: $currentBowlerWickets",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Economy: ${(currentBowlerRuns / currentBowlerOvers).roundToInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }

                // Current Over
                Text(
                    text = "Current Over",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        currentOverEvents.forEachIndexed { index, event ->
                            Text(
                                text = "Ball ${(index + 1)}: $event",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }

                // Scoring Options
                Text(
                    text = "Scoring Options",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Undo Button
                Button(
                    onClick = {
                        if (currentOverBalls > 0) {
                            currentOverBalls--
                            currentBowlerOvers = currentBowlerOvers - 1/6
                            currentOverEvents.removeLast()
                            val lastEvent = currentOverEvents.last()
                            
                            when (lastEvent) {
                                "1 Run", "2 Runs", "3 Runs", "4 Runs", "6 Runs" -> {
                                    runs -= when (lastEvent) {
                                        "1 Run" -> 1
                                        "2 Runs" -> 2
                                        "3 Runs" -> 3
                                        "4 Runs" -> 4
                                        "6 Runs" -> 6
                                        else -> 0
                                    }
                                    currentBatsman1Runs -= when (lastEvent) {
                                        "1 Run" -> 1
                                        "2 Runs" -> 2
                                        "3 Runs" -> 3
                                        "4 Runs" -> 4
                                        "6 Runs" -> 6
                                        else -> 0
                                    }
                                    currentBatsman1Balls--
                                    currentBowlerRuns -= when (lastEvent) {
                                        "1 Run" -> 1
                                        "2 Runs" -> 2
                                        "3 Runs" -> 3
                                        "4 Runs" -> 4
                                        "6 Runs" -> 6
                                        else -> 0
                                    }
                                }
                                "Wide", "No Ball" -> {
                                    runs--
                                    currentBowlerRuns--
                                }
                                "Wicket" -> {
                                    wickets--
                                }
                                "Dot Ball" -> {}
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentOverBalls > 0) PrimaryGreen else Color.White,
                        contentColor = if (currentOverBalls > 0) Color.White else PrimaryGreen
                    )
                ) {
                    Text("Undo")
                }

                // Scoring Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("Dot Ball")
                            currentBatsman1Balls++
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Dot Ball")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("1 Run")
                            runs += 1
                            currentBatsman1Runs += 1
                            currentBatsman1Balls++
                            currentBowlerRuns += 1
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("1 Run")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("2 Runs")
                            runs += 2
                            currentBatsman1Runs += 2
                            currentBatsman1Balls++
                            currentBowlerRuns += 2
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("2 Runs")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("3 Runs")
                            runs += 3
                            currentBatsman1Runs += 3
                            currentBatsman1Balls++
                            currentBowlerRuns += 3
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("3 Runs")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("4 Runs")
                            runs += 4
                            currentBatsman1Runs += 4
                            currentBatsman1Balls++
                            currentBatsman1Fours++
                            currentBowlerRuns += 4
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("4 Runs")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("6 Runs")
                            runs += 6
                            currentBatsman1Runs += 6
                            currentBatsman1Balls++
                            currentBatsman1Sixes++
                            currentBowlerRuns += 6
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("6 Runs")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("Wide")
                            runs += 1
                            currentBowlerRuns += 1
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Wide")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("No Ball")
                            runs += 1
                            currentBowlerRuns += 1
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("No Ball")
                    }
                    
                    Button(
                        onClick = {
                            currentOverBalls++
                            currentBowlerOvers += 1/6
                            currentOverEvents.add("Wicket")
                            wickets++
                            currentBowlerWickets++
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Wicket")
                    }
                }
            }
        }
    }
}
