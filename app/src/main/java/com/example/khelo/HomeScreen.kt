package com.example.khelo

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun HomeScreen(navController: NavHostController) {

    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerPanel(navController,selectedItem, onItemClick = { selectedItem = it } ) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState,
                    scope
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Column (modifier = Modifier.padding(innerPadding).fillMaxSize()){
                Row (modifier = Modifier.padding(16.dp)){
                    Text("Matches", fontSize = 20.sp)
                }

                //Match Display Cards
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    MatchDisplaySummeryCard()
                    MatchDisplaySummeryCard()
                    MatchDisplaySummeryCard()
                }

                Row (modifier = Modifier.padding(16.dp)){
                    Text("Articles", fontSize = 20.sp)
                }



            }

        }
    }
}
