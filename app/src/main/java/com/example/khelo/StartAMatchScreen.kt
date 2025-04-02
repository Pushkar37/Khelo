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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.khelo.data.storage.LocalStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartAMatchScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val localStorage = LocalStorage.getInstance(context)
    
    // Check if user is logged in
    LaunchedEffect(Unit) {
        if (!localStorage.isLoggedIn()) {
            navController.navigate("login") {
                popUpTo("StartAMatchScreen") { inclusive = true }
            }
        }
    }
    
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State for team names
    var team1Name by remember { mutableStateOf("") }
    var team2Name by remember { mutableStateOf("") }
    var groundName by remember { mutableStateOf("") }
    var overs by remember { mutableStateOf("") }
    
    // Error state
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isCreatingMatch by remember { mutableStateOf(false) }

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
                    value = team1Name,
                    onValueChange = { 
                        team1Name = it.trim() 
                    },
                    label = { Text("Team 1 Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

                // Team 2 Name Input
                OutlinedTextField(
                    value = team2Name,
                    onValueChange = { 
                        team2Name = it.trim() 
                    },
                    label = { Text("Team 2 Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
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
                    value = groundName,
                    onValueChange = { 
                        groundName = it.trim() 
                    },
                    label = { Text("Ground Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )

                // Overs Per Side Input
                OutlinedTextField(
                    value = overs,
                    onValueChange = { 
                        overs = it.trim() 
                    },
                    label = { Text("Overs Per Side") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = PrimaryGreen
                    )
                )
                
                // Error Message
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
                            // Validate inputs
                            if (team1Name.isBlank()) {
                                errorMessage = "Please enter Team 1 name"
                                return@OutlinedButton
                            }
                            
                            if (team2Name.isBlank()) {
                                errorMessage = "Please enter Team 2 name"
                                return@OutlinedButton
                            }
                            
                            if (groundName.isBlank()) {
                                errorMessage = "Please enter Ground name"
                                return@OutlinedButton
                            }
                            
                            if (overs.isBlank()) {
                                errorMessage = "Please enter number of overs"
                                return@OutlinedButton
                            }
                            
                            try {
                                val oversValue = overs.toFloat()
                                if (oversValue <= 0) {
                                    errorMessage = "Overs must be greater than 0"
                                    return@OutlinedButton
                                }
                            } catch (e: NumberFormatException) {
                                errorMessage = "Please enter a valid number for overs"
                                return@OutlinedButton
                            }
                            
                            // Proceed with match creation
                            isCreatingMatch = true
                            errorMessage = null
                            
                            // Navigate to next screen
                            navController.navigate("StartAMatchScreen2?team1Name=${team1Name}&team2Name=${team2Name}&totalOvers=${overs}") {
                                launchSingleTop = true
                            }
                        },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = PrimaryGreen
                        ),
                        enabled = !isCreatingMatch
                    ) {
                        if (isCreatingMatch) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text("Next", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartAMatchScreenPreview() {
    StartAMatchScreen(navController = rememberNavController())
}
