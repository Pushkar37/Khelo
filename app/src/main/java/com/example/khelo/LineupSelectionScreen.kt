package com.example.khelo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineupSelectionScreen(
    navController: NavHostController,
    team1Players: List<String>,
    team2Players: List<String>,
    team1Name: String = "",
    team2Name: String = "",
    tossWinner: String = "",
    tossDecision: String = "",
    team1Captain: String? = null,
    team1ViceCaptain: String? = null,
    team2Captain: String? = null,
    team2ViceCaptain: String? = null,
    totalOvers: String
) {
    // Use safe defaults for team names
    val team1NameSafe = team1Name ?: "Team 1"
    val team2NameSafe = team2Name ?: "Team 2"
    
    // Determine batting and bowling teams based on toss
    val battingTeamName = if (tossWinner == team1NameSafe && tossDecision == "Bat" || 
                             tossWinner == team2NameSafe && tossDecision == "Field") {
        team1NameSafe
    } else {
        team2NameSafe
    }
    
    val bowlingTeamName = if (battingTeamName == team1NameSafe) team2NameSafe else team1NameSafe
    val battingTeamPlayers = if (battingTeamName == team1NameSafe) team1Players else team2Players
    val bowlingTeamPlayers = if (bowlingTeamName == team1NameSafe) team1Players else team2Players
    
    // State for selections
    var batsman1 by remember { mutableStateOf("") }
    var batsman2 by remember { mutableStateOf("") }
    var bowler by remember { mutableStateOf("") }
    var wicketkeeper by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Match Lineup") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Opening Batsmen Selection
            Text(
                text = "$battingTeamName Opening Batsmen",
                style = MaterialTheme.typography.titleMedium
            )
            
            // Batsman 1 Selection
            var batsman1Expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = batsman1Expanded,
                onExpandedChange = { batsman1Expanded = it }
            ) {
                OutlinedTextField(
                    value = batsman1,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select First Batsman") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = batsman1Expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = batsman1Expanded,
                    onDismissRequest = { batsman1Expanded = false }
                ) {
                    battingTeamPlayers.forEach { player ->
                        DropdownMenuItem(
                            text = { Text(player) },
                            onClick = {
                                batsman1 = player
                                batsman1Expanded = false
                                // If batsman2 is the same as the new batsman1, clear batsman2
                                if (batsman2 == player) {
                                    batsman2 = ""
                                }
                            }
                        )
                    }
                }
            }
            
            // Batsman 2 Selection
            var batsman2Expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = batsman2Expanded,
                onExpandedChange = { batsman2Expanded = it }
            ) {
                OutlinedTextField(
                    value = batsman2,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Second Batsman") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = batsman2Expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = batsman2Expanded,
                    onDismissRequest = { batsman2Expanded = false }
                ) {
                    battingTeamPlayers
                        .filter { it != batsman1 } // Filter out the first batsman
                        .forEach { player ->
                            DropdownMenuItem(
                                text = { Text(player) },
                                onClick = {
                                    batsman2 = player
                                    batsman2Expanded = false
                                }
                            )
                        }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Bowler Selection
            Text(
                text = "$bowlingTeamName First Bowler",
                style = MaterialTheme.typography.titleMedium
            )
            
            var bowlerExpanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = bowlerExpanded,
                onExpandedChange = { bowlerExpanded = it }
            ) {
                OutlinedTextField(
                    value = bowler,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Bowler") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bowlerExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = bowlerExpanded,
                    onDismissRequest = { bowlerExpanded = false }
                ) {
                    bowlingTeamPlayers
                        .filter { it != wicketkeeper || wicketkeeper.isEmpty() } // Allow wicketkeeper to bowl only if not yet selected
                        .forEach { player ->
                            DropdownMenuItem(
                                text = { Text(player) },
                                onClick = {
                                    bowler = player
                                    bowlerExpanded = false
                                }
                            )
                        }
                }
            }
            
            // Wicketkeeper Selection
            Text(
                text = "$bowlingTeamName Wicketkeeper",
                style = MaterialTheme.typography.titleMedium
            )
            
            var wicketkeeperExpanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = wicketkeeperExpanded,
                onExpandedChange = { wicketkeeperExpanded = it }
            ) {
                OutlinedTextField(
                    value = wicketkeeper,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Wicketkeeper") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wicketkeeperExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = wicketkeeperExpanded,
                    onDismissRequest = { wicketkeeperExpanded = false }
                ) {
                    bowlingTeamPlayers
                        .filter { it != bowler || bowler.isEmpty() } // Allow bowler to be wicketkeeper only if not yet selected
                        .forEach { player ->
                            DropdownMenuItem(
                                text = { Text(player) },
                                onClick = {
                                    wicketkeeper = player
                                    wicketkeeperExpanded = false
                                }
                            )
                        }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Continue button
            Button(
                onClick = {
                    if (batsman1.isNotBlank() && batsman2.isNotBlank() && 
                        bowler.isNotBlank() && wicketkeeper.isNotBlank()) {
                        // Navigate to the scoring screen with all match information
                        navController.navigate(
                            "ScoringScreen?team1Name=${team1Name ?: ""}&team2Name=${team2Name ?: ""}" +
                            "&team1Players=${team1Players.joinToString(",")}" +
                            "&team2Players=${team2Players.joinToString(",")}" +
                            "&tossWinner=${tossWinner ?: ""}&tossDecision=${tossDecision ?: ""}" +
                            "&team1Captain=${team1Captain ?: ""}&team1ViceCaptain=${team1ViceCaptain ?: ""}" +
                            "&team2Captain=${team2Captain ?: ""}&team2ViceCaptain=${team2ViceCaptain ?: ""}" +
                            "&striker=${batsman1}&nonStriker=${batsman2}" +
                            "&bowler=${bowler}&wicketkeeper=${wicketkeeper}" +
                            "&battingTeamName=${battingTeamName}&bowlingTeamName=${bowlingTeamName}" +
                            "&totalOvers=${totalOvers}"
                        )
                    } else {
                        // Optionally, show a message to the user (snackbar, toast, etc.)
                    }
                },
                enabled = batsman1.isNotBlank() && batsman2.isNotBlank() && bowler.isNotBlank() && wicketkeeper.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(PrimaryGreen)
            ) {
                Text("Start Match", fontWeight = FontWeight.Medium)
            }
        }
    }
}
