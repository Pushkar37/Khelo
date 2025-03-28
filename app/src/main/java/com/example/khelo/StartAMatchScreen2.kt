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
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen

@Composable
fun StartAMatchScreen2(navController: NavHostController) {

    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


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
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ){
                    Text(text = "Add Team 1 Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    TextField(
                        value = team1player,
                        onValueChange = {
                            team1player = it
                            // Add item to list when user presses enter
                            if (it.endsWith("\n")) {
                                val newItem = it.trimEnd('\n')
                                if (newItem.isNotEmpty()) {
                                    enteredItems = enteredItems.toMutableMap().apply {
                                        this["team1Players"] = this["team1Players"]!!.toMutableList().apply {
                                            add(newItem)
                                        }
                                    }
                                    team1player = ""
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, RectangleShape)
                            .padding(vertical = 8.dp)
                    )

                    Text(text = "Add Team 2 Player", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    TextField(
                        value = team2player,
                        onValueChange = {
                            team2player = it
                            if (it.endsWith("\n")) {
                                val newItem = it.trimEnd('\n')
                                if (newItem.isNotEmpty()) {
                                    enteredItems = enteredItems.toMutableMap().apply {
                                        this["team2Players"] = this["team2Players"]!!.toMutableList().apply {
                                            add(newItem)
                                        }
                                    }
                                    team2player = ""
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, RectangleShape)
                            .padding(vertical = 8.dp)
                    )

                    // Display entered items
                    Text(text = "Full Squad", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    // Team 1 Players
                    if (enteredItems["team1Players"]!!.isNotEmpty()) {
                        Text(text = "Team 1 Players", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                        enteredItems["team1Players"]!!.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        // Handle item click (edit)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        enteredItems = enteredItems.toMutableMap().apply {
                                            this["team1Players"] = this["team1Players"]!!.toMutableList().apply {
                                                removeAt(index)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete item",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }

                    // Team 2 Players
                    if (enteredItems["team2Players"]!!.isNotEmpty()) {
                        Text(text = "Team 2 Players", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                        enteredItems["team2Players"]!!.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        // Handle item click (edit)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        enteredItems = enteredItems.toMutableMap().apply {
                                            this["team2Players"] = this["team2Players"]!!.toMutableList().apply {
                                                removeAt(index)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete item",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }

                    Column (verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ){
                        Row (horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ){
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
                                onClick = { TODO() },
                                border = BorderStroke(1.dp, PrimaryGreen),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.White,
                                    containerColor = PrimaryGreen
                                ),
                            ){
                                Text("Next", fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }
    }

}