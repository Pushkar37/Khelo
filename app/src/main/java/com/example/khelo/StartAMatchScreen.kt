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
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartAMatchScreen(
    navController: NavHostController
) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State for team names
    var team1Name by remember { mutableStateOf("") }
    var team2Name by remember { mutableStateOf("") }
    var groundName by remember { mutableStateOf("") }
    var overs by remember { mutableStateOf("") }

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
                // Team Name Input
                Text(
                    text = "Enter Team Names",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen
                )

                // Team 1 Name Input
                OutlinedTextField(
                    value = team1Name.trim(),
                    onValueChange = { 
                        team1Name = it.trim() 
                    },
                    label = { Text("Team 1 Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

                // Team 2 Name Input
                OutlinedTextField(
                    value = team2Name.trim(),
                    onValueChange = { 
                        team2Name = it.trim() 
                    },
                    label = { Text("Team 2 Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

                // Ground Details
                Text(
                    text = "Ground Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Ground Name Input
                OutlinedTextField(
                    value = groundName.trim(),
                    onValueChange = { 
                        groundName = it.trim() 
                    },
                    label = { Text("Ground Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

                // Overs Per Side Input
                OutlinedTextField(
                    value = overs.trim(),
                    onValueChange = { 
                        overs = it.trim() 
                    },
                    label = { Text("Overs Per Side") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

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
                            if (team1Name.isNotBlank() && team2Name.isNotBlank() && 
                                groundName.isNotBlank() && overs.isNotBlank()) {
                                navController.navigate("StartAMatchScreen2?team1Name=${team1Name}&team2Name=${team2Name}") {
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
