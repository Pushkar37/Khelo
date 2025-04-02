package com.example.khelo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import com.example.khelo.StartAMatchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TossAndPlaying11Screen(
    navController: NavHostController,
    team1Players: List<String>,
    team2Players: List<String>,
    team1Name: String,
    team2Name: String,
    totalOvers: String
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Toss state
    var tossWinner by remember { mutableStateOf("") }
    var tossDecision by remember { mutableStateOf("") }
    val tossOptions = listOf("Bat", "Field")

    // Playing 11 state
    var selectedTeam1Players by remember { mutableStateOf(emptyList<String>()) }
    var selectedTeam2Players by remember { mutableStateOf(emptyList<String>()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerPanel(navController, selectedItem, onItemClick = { selectedItem = it }) }
    ) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                if (selectedItem == "StartAMatchScreen2") {
                    StartAMatchScreenContent(
                        team1Players = team1Players,
                        team2Players = team2Players,
                        team1Name = team1Name,
                        team2Name = team2Name,
                        onPlaying11Selected = { team1Selected, team2Selected ->
                            selectedTeam1Players = team1Selected
                            selectedTeam2Players = team2Selected
                            showBottomSheet = false
                            navController.navigate("CaptainSelectionScreen?team1Players=${team1Selected.joinToString(",")}&team2Players=${team2Selected.joinToString(",")}&team1Name=${team1Name}&team2Name=${team2Name}&tossWinner=${tossWinner}&tossDecision=${tossDecision}&totalOvers=${totalOvers}")
                        }
                    )
                }
            }
        }
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
                            text = "Select Playing 11",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Playing 11 selection
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
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
                        }
                    }
                }

                // Action Button
                Button(
                    onClick = {
                        if (tossWinner.isNotEmpty() && tossDecision.isNotEmpty() &&
                            selectedTeam1Players.size == 11 && selectedTeam2Players.size == 11) {
                            // Navigate to CaptainSelectionScreen with selected players
                            navController.navigate(
                                "CaptainSelectionScreen?team1Players=${selectedTeam1Players.joinToString(",")}&team2Players=${selectedTeam2Players.joinToString(",")}&team1Name=$team1Name&team2Name=$team2Name&tossWinner=$tossWinner&tossDecision=$tossDecision&totalOvers=$totalOvers"
                            )
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

@Composable
private fun StartAMatchScreenContent(
    team1Players: List<String>,
    team2Players: List<String>,
    team1Name: String,
    team2Name: String,
    onPlaying11Selected: (List<String>, List<String>) -> Unit
) {
    var selectedTeam1Players by remember { mutableStateOf(emptyList<String>()) }
    var selectedTeam2Players by remember { mutableStateOf(emptyList<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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

        Button(
            onClick = {
                if (selectedTeam1Players.size == 11 && selectedTeam2Players.size == 11) {
                    onPlaying11Selected(selectedTeam1Players, selectedTeam2Players)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Continue")
        }
    }
}
