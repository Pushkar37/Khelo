package com.example.khelo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun StartATournamentScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerPanel(navController, selectedItem, onItemClick = { selectedItem = it }) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState,
                    scope,
                    navController,
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(innerPadding)){
                Text("Start A Tournament Screen", fontSize = 32.sp)
                Icon(imageVector = Icons.Default.Add , contentDescription = "" , modifier = Modifier.size(200.dp))
            }
        }
    }
}
