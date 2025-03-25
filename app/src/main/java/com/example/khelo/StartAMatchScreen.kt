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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun StartAMatchScreen(navController: NavHostController) {
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
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)){
                OutlinedTextField(value = "Team A", onValueChange = {} , modifier = Modifier.fillMaxWidth(.9f), label = {Text("Home Team", modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.White, shape = RectangleShape).padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = "Team B", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Visitor Team", modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.White, shape = RectangleShape).padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = "", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Ground Name", modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.White, shape = RectangleShape).padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = "", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Overs Per Side", modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.White, shape = RectangleShape).padding(horizontal = 10.dp))})

                Spacer(Modifier.padding(vertical = 10.dp))
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(.9f)){
                    Button(onClick = {navController.navigate(StartAMatchScreen2.route)}, shape = RoundedCornerShape(10.dp), modifier = Modifier.size(width = 150.dp , height = 50.dp)) {Text("Next")}
                }
            }
        }
    }
}

@Preview
@Composable
fun StartAMatchScreen1(){
    Scaffold { innerPadding ->
        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)){
            OutlinedTextField(value = "Team A", onValueChange = {} , modifier = Modifier.fillMaxWidth(.9f), label = {Text("Home Team")})

            Spacer(Modifier.padding(vertical = 10.dp))
            OutlinedTextField(value = "Team B", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Visitor Team")})

            Spacer(Modifier.padding(vertical = 10.dp))
            OutlinedTextField(value = "", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Ground Name")})

            Spacer(Modifier.padding(vertical = 10.dp))
            OutlinedTextField(value = "", onValueChange = {} ,modifier = Modifier.fillMaxWidth(.9f), label = {Text("Overs Per Side")})

            Spacer(Modifier.padding(vertical = 10.dp))
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(.9f)){
                Button(onClick = {}, shape = RoundedCornerShape(10.dp), modifier = Modifier.size(width = 150.dp , height = 50.dp)) {Text("Next")}
            }
        }
    }
}