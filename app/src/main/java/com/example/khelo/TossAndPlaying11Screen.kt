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

@Composable
fun TossAndPlaying11Screen(
    navController: NavHostController,
    team1Players: List<String>,
    team2Players: List<String>,
    team1Name: String,
    team2Name: String
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Toss state
    var tossWinner by remember { mutableStateOf("") }
    var tossDecision by remember { mutableStateOf("") }
    val tossOptions = listOf("Bat", "Field")

    // Playing 11 state
    var selectedTeam1Players by remember { mutableStateOf(emptyList<String>()) }
    var selectedTeam2Players by remember { mutableStateOf(emptyList<String>()) }

    // Captain and Vice-Captain state
    var team1Captain by remember { mutableStateOf("") }
    var team1ViceCaptain by remember { mutableStateOf("") }
    var team2Captain by remember { mutableStateOf("") }
    var team2ViceCaptain by remember { mutableStateOf("") }

    // Captain and Vice-Captain dropdown state
    var team1CaptainExpanded by remember { mutableStateOf(false) }
    var team1ViceCaptainExpanded by remember { mutableStateOf(false) }
    var team2CaptainExpanded by remember { mutableStateOf(false) }
    var team2ViceCaptainExpanded by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerPanel(navController, selectedItem, onItemClick = { selectedItem = it }) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState,
                    scope,
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
                // Toss Section
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
                            text = "Toss",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Toss Winner Selection
                        Text(
                            text = "Select Toss Winner",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { tossWinner = team1Name },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tossWinner == team1Name) PrimaryGreen else Color.White,
                                    contentColor = if (tossWinner == team1Name) Color.White else PrimaryGreen
                                )
                            ) {
                                Text(team1Name)
                            }
                            Button(
                                onClick = { tossWinner = team2Name },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tossWinner == team2Name) PrimaryGreen else Color.White,
                                    contentColor = if (tossWinner == team2Name) Color.White else PrimaryGreen
                                )
                            ) {
                                Text(team2Name)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Toss Decision Selection
                        Text(
                            text = "Select Toss Decision",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { tossDecision = "Bat" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tossDecision == "Bat") PrimaryGreen else Color.White,
                                    contentColor = if (tossDecision == "Bat") Color.White else PrimaryGreen
                                )
                            ) {
                                Text("Bat")
                            }
                            Button(
                                onClick = { tossDecision = "Field" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tossDecision == "Field") PrimaryGreen else Color.White,
                                    contentColor = if (tossDecision == "Field") Color.White else PrimaryGreen
                                )
                            ) {
                                Text("Field")
                            }
                        }
                    }
                }

                // Playing 11 Section
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
                            text = "Select Playing 11 and Captain",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Team 1 Players
                        Text(
                            text = "$team1Name Players",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Selected: ${selectedTeam1Players.size}/11",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        team1Players.forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f),
                                    color = Color.White
                                )
                                Row(
                                    modifier = Modifier.weight(0.5f),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Checkbox(
                                        checked = selectedTeam1Players.contains(player),
                                        onCheckedChange = { checked ->
                                            if (checked) {
                                                if (selectedTeam1Players.size < 11) {
                                                    selectedTeam1Players = selectedTeam1Players + player
                                                }
                                            } else {
                                                selectedTeam1Players = selectedTeam1Players - player
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Team 1 Captain Selection
                        Text(
                            text = "Select Captain",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        DropdownMenu(
                            items = selectedTeam1Players,
                            selectedItem = team1Captain,
                            onItemSelected = { 
                                team1Captain = it
                                team1CaptainExpanded = false
                            },
                            expanded = team1CaptainExpanded,
                            onExpandedChange = { team1CaptainExpanded = it }
                        )

                        // Team 1 Vice-Captain Selection
                        Text(
                            text = "Select Vice-Captain",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        DropdownMenu(
                            items = selectedTeam1Players.filter { it != team1Captain },
                            selectedItem = team1ViceCaptain,
                            onItemSelected = { 
                                team1ViceCaptain = it
                                team1ViceCaptainExpanded = false
                            },
                            expanded = team1ViceCaptainExpanded,
                            onExpandedChange = { team1ViceCaptainExpanded = it }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Team 2 Players
                        Text(
                            text = "$team2Name Players",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Selected: ${selectedTeam2Players.size}/11",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        team2Players.forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f),
                                    color = Color.White
                                )
                                Row(
                                    modifier = Modifier.weight(0.5f),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Checkbox(
                                        checked = selectedTeam2Players.contains(player),
                                        onCheckedChange = { checked ->
                                            if (checked) {
                                                if (selectedTeam2Players.size < 11) {
                                                    selectedTeam2Players = selectedTeam2Players + player
                                                }
                                            } else {
                                                selectedTeam2Players = selectedTeam2Players - player
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Team 2 Captain Selection
                        Text(
                            text = "Select Captain",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        DropdownMenu(
                            items = selectedTeam2Players,
                            selectedItem = team2Captain,
                            onItemSelected = { 
                                team2Captain = it
                                team2CaptainExpanded = false
                            },
                            expanded = team2CaptainExpanded,
                            onExpandedChange = { team2CaptainExpanded = it }
                        )

                        // Team 2 Vice-Captain Selection
                        Text(
                            text = "Select Vice-Captain",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        DropdownMenu(
                            items = selectedTeam2Players.filter { it != team2Captain },
                            selectedItem = team2ViceCaptain,
                            onItemSelected = { 
                                team2ViceCaptain = it
                                team2ViceCaptainExpanded = false
                            },
                            expanded = team2ViceCaptainExpanded,
                            onExpandedChange = { team2ViceCaptainExpanded = it }
                        )
                    }
                }

                // Action Button
                Button(
                    onClick = {
                        if (tossWinner.isNotEmpty() && tossDecision.isNotEmpty() &&
                            selectedTeam1Players.size == 11 && selectedTeam2Players.size == 11 &&
                            team1Captain.isNotBlank() && team1ViceCaptain.isNotBlank() &&
                            team2Captain.isNotBlank() && team2ViceCaptain.isNotBlank()) {
                            navController.navigate("OpeningPlayersScreen?team1Name=${team1Name}&team2Name=${team2Name}&tossWinner=${tossWinner}&tossDecision=${tossDecision}&team1Players=${selectedTeam1Players.joinToString(",")}&team2Players=${selectedTeam2Players.joinToString(",")}&team1Captain=${team1Captain}&team1ViceCaptain=${team1ViceCaptain}&team2Captain=${team2Captain}&team2ViceCaptain=${team2ViceCaptain}&team1CaptainName=${team1Captain}&team1ViceCaptainName=${team1ViceCaptain}&team2CaptainName=${team2Captain}&team2ViceCaptainName=${team2ViceCaptain}") {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(PrimaryGreen)
                ) {
                    Text(
                        text = "Next",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
