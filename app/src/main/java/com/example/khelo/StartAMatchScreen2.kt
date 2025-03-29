package com.example.khelo

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.unit.sp

@Composable
fun StartAMatchScreen2(
    navController: NavHostController,
    team1Name: String,
    team2Name: String
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State for player input
    var team1player by remember { mutableStateOf("") }
    var team2player by remember { mutableStateOf("") }

    // State to store entered items
    var enteredItems by remember { mutableStateOf(mapOf(
        "team1Players" to emptyList<String>(),
        "team2Players" to emptyList<String>(),
    )) }

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
            ){
                // Input Fields
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Team 1 Player Input
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Add ${team1Name} Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        TextField(
                            value = team1player.trim(),
                            onValueChange = {
                                team1player = it.trim()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("${team1Name} Player Name") }
                        )
                        
                        // Add Team 1 Player Button
                        Button(
                            onClick = {
                                if (team1player.isNotBlank()) {
                                    val currentTeam1Players = enteredItems["team1Players"] ?: emptyList()
                                    enteredItems = enteredItems.toMutableMap().apply {
                                        this["team1Players"] = currentTeam1Players + team1player
                                    }
                                    team1player = ""
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(PrimaryGreen)
                        ) {
                            Text("Add ${team1Name} Player")
                        }
                    }

                    // Team 2 Player Input
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Add ${team2Name} Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        TextField(
                            value = team2player.trim(),
                            onValueChange = {
                                team2player = it.trim()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = { Text("${team2Name} Player Name") }
                        )
                        
                        // Add Team 2 Player Button
                        Button(
                            onClick = {
                                if (team2player.isNotBlank()) {
                                    val currentTeam2Players = enteredItems["team2Players"] ?: emptyList()
                                    enteredItems = enteredItems.toMutableMap().apply {
                                        this["team2Players"] = currentTeam2Players + team2player
                                    }
                                    team2player = ""
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(PrimaryGreen)
                        ) {
                            Text("Add ${team2Name} Player")
                        }
                    }
                }

                // Display Teams with player cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Team 1 Players List
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = team1Name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        (enteredItems["team1Players"] ?: emptyList()).forEach { player ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = player,
                                        modifier = Modifier.weight(1f),
                                        color = Color.White
                                    )
                                    IconButton(
                                        onClick = {
                                            val currentTeam1Players = enteredItems["team1Players"] ?: emptyList()
                                            enteredItems = enteredItems.toMutableMap().apply {
                                                this["team1Players"] = currentTeam1Players - player
                                            }
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete ${team1Name} player",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Team 2 Players List
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = team2Name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        (enteredItems["team2Players"] ?: emptyList()).forEach { player ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = PrimaryGreen)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = player,
                                        modifier = Modifier.weight(1f),
                                        color = Color.White
                                    )
                                    IconButton(
                                        onClick = {
                                            val currentTeam2Players = enteredItems["team2Players"] ?: emptyList()
                                            enteredItems = enteredItems.toMutableMap().apply {
                                                this["team2Players"] = currentTeam2Players - player
                                            }
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete ${team2Name} player",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
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
                        onClick = { 
                            navController.navigateUp() 
                        },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        ),
                    ){
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                        Text("Back", fontWeight = FontWeight.Medium)
                    }

                    OutlinedButton(
                        onClick = {
                            if ((enteredItems["team1Players"] ?: emptyList()).isNotEmpty() &&
                                (enteredItems["team2Players"] ?: emptyList()).isNotEmpty()) {
                                navController.navigate("TossAndPlaying11Screen?team1Players=${(enteredItems["team1Players"] ?: emptyList()).joinToString(",")}&team2Players=${(enteredItems["team2Players"] ?: emptyList()).joinToString(",")}&team1Name=${team1Name}&team2Name=${team2Name}") {
                                    launchSingleTop = true
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