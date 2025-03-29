package com.example.khelo

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpeningPlayersScreen(
    navController: NavHostController,
    team1Name: String,
    team2Name: String,
    tossWinner: String,
    tossDecision: String,
    team1Players: List<String>,
    team2Players: List<String>,
    team1Captain: String,
    team1ViceCaptain: String,
    team2Captain: String,
    team2ViceCaptain: String
) {
    var openingBatsman1 by remember { mutableStateOf("") }
    var openingBatsman2 by remember { mutableStateOf("") }
    var openingBowler by remember { mutableStateOf("") }
    var wicketKeeper by remember { mutableStateOf("") }
    var battingTeam by remember { mutableStateOf("") }
    var bowlingTeam by remember { mutableStateOf("") }
    
    var openingBatsman1Expanded by remember { mutableStateOf(false) }
    var openingBatsman2Expanded by remember { mutableStateOf(false) }
    var openingBowlerExpanded by remember { mutableStateOf(false) }
    var wicketKeeperExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Determine batting and bowling teams based on toss
        if (tossWinner == team1Name) {
            battingTeam = if (tossDecision == "bat") team1Name else team2Name
            bowlingTeam = if (tossDecision == "bat") team2Name else team1Name
        } else {
            battingTeam = if (tossDecision == "bat") team2Name else team1Name
            bowlingTeam = if (tossDecision == "bat") team1Name else team2Name
        }
    }

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        drawerContent = { DrawerPanel(navController, "Item 1", onItemClick = { }) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Opening Players") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Match Info
                Text(
                    text = "${team1Name} vs ${team2Name}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Batting Team Section
                Text(
                    text = "$battingTeam is batting first",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Opening Batsmen Selection
                Text(
                    text = "Select Opening Batsmen",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                // Batsman 1 Selection
                Text(
                    text = "Opening Batsman 1",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                DropdownMenu(
                    items = if (battingTeam == team1Name) team1Players else team2Players,
                    selectedItem = openingBatsman1,
                    onItemSelected = { 
                        openingBatsman1 = it
                        openingBatsman1Expanded = false
                    },
                    expanded = openingBatsman1Expanded,
                    onExpandedChange = { openingBatsman1Expanded = it }
                )

                // Batsman 2 Selection
                Text(
                    text = "Opening Batsman 2",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                DropdownMenu(
                    items = if (battingTeam == team1Name) team1Players.filter { it != openingBatsman1 } else team2Players.filter { it != openingBatsman1 },
                    selectedItem = openingBatsman2,
                    onItemSelected = { 
                        openingBatsman2 = it
                        openingBatsman2Expanded = false
                    },
                    expanded = openingBatsman2Expanded,
                    onExpandedChange = { openingBatsman2Expanded = it }
                )

                // Bowling Team Section
                Text(
                    text = "$bowlingTeam is bowling first",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Opening Bowler Selection
                Text(
                    text = "Select Opening Bowler",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                DropdownMenu(
                    items = if (bowlingTeam == team1Name) team1Players else team2Players,
                    selectedItem = openingBowler,
                    onItemSelected = { 
                        openingBowler = it
                        openingBowlerExpanded = false
                    },
                    expanded = openingBowlerExpanded,
                    onExpandedChange = { openingBowlerExpanded = it }
                )

                // Wicket Keeper Selection
                Text(
                    text = "Select Wicket Keeper",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                DropdownMenu(
                    items = if (battingTeam == team1Name) team1Players else team2Players,
                    selectedItem = wicketKeeper,
                    onItemSelected = { 
                        wicketKeeper = it
                        wicketKeeperExpanded = false
                    },
                    expanded = wicketKeeperExpanded,
                    onExpandedChange = { wicketKeeperExpanded = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Button
                Button(
                    onClick = {
                        if (openingBatsman1.isNotBlank() && openingBatsman2.isNotBlank() &&
                            openingBowler.isNotBlank() && wicketKeeper.isNotBlank()) {
                            navController.navigate(
                                "MatchScoringScreen?" +
                                "team1Name=${team1Name}&" +
                                "team2Name=${team2Name}&" +
                                "tossWinner=${tossWinner}&" +
                                "tossDecision=${tossDecision}&" +
                                "openingBatsman1=${openingBatsman1}&" +
                                "openingBatsman2=${openingBatsman2}&" +
                                "openingBowler=${openingBowler}&" +
                                "wicketKeeper=${wicketKeeper}&" +
                                "battingTeam=${battingTeam}&" +
                                "bowlingTeam=${bowlingTeam}&" +
                                "team1Captain=${team1Captain}&" +
                                "team1ViceCaptain=${team1ViceCaptain}&" +
                                "team2Captain=${team2Captain}&" +
                                "team2ViceCaptain=${team2ViceCaptain}"
                            ) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(PrimaryGreen)
                ) {
                    Text(
                        text = "Start Match",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(expanded) }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Player") },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown menu"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                    onExpandedChange(expanded)
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
