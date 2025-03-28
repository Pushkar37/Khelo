package com.example.khelo

import android.graphics.Color
import android.graphics.Color.BLUE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen

@Composable
fun StartAMatchScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var team1 by remember { mutableStateOf("") }
    var team2 by remember { mutableStateOf("") }
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
        ) { innerPadding ->
            Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)){
                OutlinedTextField(value = team1, onValueChange = {team1 = it} , modifier = Modifier.fillMaxWidth(.9f), colors = OutlinedTextFieldDefaults.colors(focusedLabelColor = PrimaryGreen), label = {Text("Home Team", modifier = Modifier
                    .background(
                        color = androidx.compose.ui.graphics.Color.White,
                        shape = RectangleShape
                    )
                    .padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = team2, onValueChange = {team2 = it} ,modifier = Modifier.fillMaxWidth(.9f),colors = OutlinedTextFieldDefaults.colors(focusedLabelColor = PrimaryGreen), label = {Text("Visitor Team", modifier = Modifier
                    .background(
                        color = androidx.compose.ui.graphics.Color.White,
                        shape = RectangleShape
                    )
                    .padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = groundName, onValueChange = {groundName = it} ,modifier = Modifier.fillMaxWidth(.9f),colors = OutlinedTextFieldDefaults.colors(focusedLabelColor = PrimaryGreen), label = {Text("Ground Name", modifier = Modifier
                    .background(
                        color = androidx.compose.ui.graphics.Color.White,
                        shape = RectangleShape
                    )
                    .padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = overs, onValueChange = {overs = it } ,modifier = Modifier.fillMaxWidth(.9f),colors = OutlinedTextFieldDefaults.colors(focusedLabelColor = PrimaryGreen), label = {Text("Overs Per Side", modifier = Modifier
                    .background(
                        color = androidx.compose.ui.graphics.Color.White,
                        shape = RectangleShape
                    )
                    .padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(.9f)){
                    Button(
                        onClick = {
                            if (team1.isNotBlank() && team2.isNotBlank() && groundName.isNotBlank() && overs.isNotBlank()) {
                                try {
                                    navController.navigate("StartAMatchScreen2?team1Name=${team1}&team2Name=${team2}") {
                                        launchSingleTop = true
                                    }
                                } catch (e: Exception) {
                                    println("Navigation error: ${e.message}")
                                }
                            }
                        }, 
                        shape = RoundedCornerShape(10.dp), 
                        colors = ButtonDefaults.buttonColors(PrimaryGreen), 
                        modifier = Modifier.size(120.dp, 50.dp)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}
