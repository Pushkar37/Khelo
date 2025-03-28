package com.example.khelo

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun TossAndPlaying11Screen(
    navController: NavHostController,
    team1Players: List<String>,
    team2Players: List<String>,
    team1Name: String = "Team 1",
    team2Name: String = "Team 2"
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var tossWinner by remember { mutableStateOf("") }
    var tossDecision by remember { mutableStateOf("") }
    val tossOptions = listOf("Bat", "Field")
    var expanded by remember { mutableStateOf(false) }

    // State for playing 11 selection
    var selectedTeam1Players by remember { mutableStateOf(emptyList<String>()) }
    var selectedTeam2Players by remember { mutableStateOf(emptyList<String>()) }

    val snackbarHostState = remember { SnackbarHostState() }

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
            bottomBar = { BottomNavigationBar(navController) },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Team Names
                Text(
                    text = "$team1Name vs $team2Name",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                // Toss Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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

                        // Toss Winner Selection
                        Text(
                            text = "Toss Winner",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Team 1 Radio Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = tossWinner == team1Name,
                                onClick = { tossWinner = team1Name }
                            )
                            Text(
                                text = team1Name,
                                modifier = Modifier.padding(start = 8.dp),
                                color = Color.White
                            )
                        }

                        // Team 2 Radio Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = tossWinner == team2Name,
                                onClick = { tossWinner = team2Name }
                            )
                            Text(
                                text = team2Name,
                                modifier = Modifier.padding(start = 8.dp),
                                color = Color.White
                            )
                        }

                        // Toss Decision
                        if (tossWinner.isNotEmpty()) {
                            Text(
                                text = "Decision",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            
                            // Bat/Field Selection
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
                }

                // Playing 11 Selection
                Text(
                    text = "Select Playing 11",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                // Team 1 Playing 11
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = team1Name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        
                        team1Players.forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f),
                                    color = Color.White
                                )
                                Checkbox(
                                    checked = selectedTeam1Players.contains(player),
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            selectedTeam1Players = selectedTeam1Players + player
                                        } else {
                                            selectedTeam1Players = selectedTeam1Players - player
                                        }
                                    }
                                )
                            }
                        }

                        Text(
                            text = "Selected: ${selectedTeam1Players.size}/11",
                            modifier = Modifier.padding(top = 8.dp),
                            color = Color.White
                        )
                    }
                }

                // Team 2 Playing 11
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = team2Name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        
                        team2Players.forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f),
                                    color = Color.White
                                )
                                Checkbox(
                                    checked = selectedTeam2Players.contains(player),
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            selectedTeam2Players = selectedTeam2Players + player
                                        } else {
                                            selectedTeam2Players = selectedTeam2Players - player
                                        }
                                    }
                                )
                            }
                        }

                        Text(
                            text = "Selected: ${selectedTeam2Players.size}/11",
                            modifier = Modifier.padding(top = 8.dp),
                            color = Color.White
                        )
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = {  },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        ),
                    ){
                        Text("Skip", fontWeight = FontWeight.Medium)
                    }

                    OutlinedButton(
                        onClick = {
                            if (tossWinner.isNotEmpty() && tossDecision.isNotEmpty() &&
                                selectedTeam1Players.size == 11 && selectedTeam2Players.size == 11) {
                                // Navigate to MatchScoringScreen with all data
                                navController.navigate("MatchScoringScreen") {
                                    launchSingleTop = true
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Please complete all selections"
                                    )
                                }
                            }
                        },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = PrimaryGreen
                        ),
                    ) {
                        Text("Next", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}
