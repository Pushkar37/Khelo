package com.example.khelo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.example.khelo.ui.theme.PrimaryGreen
import android.widget.Toast
import androidx.compose.material3.SnackbarHost
import kotlinx.coroutines.launch
import java.io.Serializable

data class PlayerData(val team1Players: List<String>, val team2Players: List<String>) : Serializable

@Composable
fun StartAMatchScreen2(navController: NavHostController, team1Name: String = "Team 1", team2Name: String = "Team 2") {

    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var team1player by remember { mutableStateOf("") }
    var team2player by remember { mutableStateOf("") }

    // State to store entered items
    var enteredItems by remember { mutableStateOf(mapOf(
        "team1Players" to emptyList<String>(),
        "team2Players" to emptyList<String>(),
    )) }

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
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState,
                    scope,
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ){
                    Text(text = "Add $team1Name Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    TextField(
                        value = team1player,
                        onValueChange = {
                            team1player = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = { Text("$team1Name Player Name") }
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
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Add $team1Name Player")
                    }

                    Text(text = "Add $team2Name Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    TextField(
                        value = team2player,
                        onValueChange = {
                            team2player = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = { Text("$team2Name Player Name") }
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
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(PrimaryGreen)
                    ) {
                        Text("Add $team2Name Player")
                    }

                    // Display Team 1 Players
                    Text(text = "$team1Name Players", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        (enteredItems["team1Players"] ?: emptyList()).forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        val currentTeam1Players = enteredItems["team1Players"] ?: emptyList()
                                        enteredItems = enteredItems.toMutableMap().apply {
                                            this["team1Players"] = currentTeam1Players - player
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete player"
                                    )
                                }
                            }
                        }
                    }

                    // Display Team 2 Players
                    Text(text = "$team2Name Players", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        (enteredItems["team2Players"] ?: emptyList()).forEach { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = player,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        val currentTeam2Players = enteredItems["team2Players"] ?: emptyList()
                                        enteredItems = enteredItems.toMutableMap().apply {
                                            this["team2Players"] = currentTeam2Players - player
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete player"
                                    )
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
                                // Get the current player lists
                                val team1Players = enteredItems["team1Players"] ?: emptyList()
                                val team2Players = enteredItems["team2Players"] ?: emptyList()
                                
                                if (team1Players.isNotEmpty() && team2Players.isNotEmpty()) {
                                    // Convert lists to comma-separated strings
                                    val team1 = team1Players.joinToString(",")
                                    val team2 = team2Players.joinToString(",")
                                    
                                    // Navigate to TossAndPlaying11Screen with the player data and team names
                                    navController.navigate("TossAndPlaying11Screen?team1Players=$team1&team2Players=$team2&team1Name=${team1Name}&team2Name=${team2Name}") {
                                        launchSingleTop = true
                                    }
                                } else {
                                    // Show a toast or snackbar if no players are added
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Please add at least one player to both teams"
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
}