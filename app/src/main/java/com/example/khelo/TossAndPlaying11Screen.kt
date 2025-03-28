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

// Import PlayerData class

@Composable
fun TossAndPlaying11Screen(
    navController: NavHostController,
    team1Players: List<String>,
    team2Players: List<String>
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var tossWinner by remember { mutableStateOf("") }
    var tossDecision by remember { mutableStateOf("") }
    val tossOptions = listOf("Bat", "Field")
    var expanded by remember { mutableStateOf(false) }

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
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            team1Players.forEach { player ->
                                DropdownMenuItem(
                                    text = { Text(player) },
                                    onClick = {
                                        tossWinner = player
                                        expanded = false
                                    }
                                )
                            }
                            team2Players.forEach { player ->
                                DropdownMenuItem(
                                    text = { Text(player) },
                                    onClick = {
                                        tossWinner = player
                                        expanded = false
                                    }
                                )
                            }
                        }

                        // Toss Decision
                        Text(
                            text = "Toss Decision",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            tossOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = tossDecision == option,
                                        onCheckedChange = { tossDecision = option }
                                    )
                                    Text(option)
                                }
                            }
                        }
                    }
                }

                // Team 1 Playing 11
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Team 1 Playing 11",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    // List of selected players
                    team1Players.forEach { player ->
                        Text(
                            text = player,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Team 2 Playing 11
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Team 2 Playing 11",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    // List of selected players
                    team2Players.forEach { player ->
                        Text(
                            text = player,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Next Button
                Button(
                    onClick = {
                        if (tossWinner.isNotEmpty() && tossDecision.isNotEmpty()) {
                            navController.navigate(MatchScoringScreen.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = tossWinner.isNotEmpty() && tossDecision.isNotEmpty()
                ) {
                    Text("Start Match", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
