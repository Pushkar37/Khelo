package com.example.khelo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Remove/
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen

@Composable
fun MatchScoringScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Match state
    var currentBatsman by remember { mutableStateOf("Player 1") }
    var currentBowler by remember { mutableStateOf("Player 2") }
    var runs by remember { mutableStateOf(0) }
    var wickets by remember { mutableStateOf(0) }
    var overs by remember { mutableStateOf(0) }
    var balls by remember { mutableStateOf(0) }
    var extras by remember { mutableStateOf(0) }
    var wides by remember { mutableStateOf(0) }
    var noBalls by remember { mutableStateOf(0) }

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
            ) {
                // Score Display
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${runs}/${wickets}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryGreen
                    )
                    Text(
                        "$overs.$balls",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryGreen
                    )
                }

                // Current Batsman and Bowler
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Batsman: $currentBatsman",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Bowler: $currentBowler",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Score Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { runs += 1; balls++ },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("1")
                    }
                    Button(
                        onClick = { runs += 2; balls++ },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("2")
                    }
                    Button(
                        onClick = { runs += 3; balls++ },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("3")
                    }
                    Button(
                        onClick = { runs += 4; balls++ },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("4")
                    }
                    Button(
                        onClick = { runs += 6; balls++ },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("6")
                    }
                }

                // Extras and Wickets
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { 
                            extras += 1; 
                            wides += 1; 
                            balls++ 
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Wide")
                    }
                    Button(
                        onClick = { 
                            extras += 1; 
                            noBalls += 1; 
                            balls++ 
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("No Ball")
                    }
                    Button(
                        onClick = { 
                            wickets += 1; 
                            balls++ 
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Wicket")
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = { 
                            // Change batsman
                            // TODO: Implement batsman change logic
                        },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        )
                    ) {
                        Text("Change Batsman")
                    }

                    OutlinedButton(
                        onClick = { 
                            // Change bowler
                            // TODO: Implement bowler change logic
                        },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        )
                    ) {
                        Text("Change Bowler")
                    }
                }
            }
        }
    }
}
