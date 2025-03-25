package com.example.khelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun StartAMatchScreen2(navController: NavHostController) {

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
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)){
                Text("Enter Player Details", fontSize = 30.sp , fontWeight = FontWeight.Bold , color = Color.Green , letterSpacing = 2.sp)
                Spacer(Modifier.height(20.dp))
                Text("Enter Players of Team 1", fontSize = 15.sp)

                Spacer(Modifier.height(20.dp))
                OutlinedTextField(value = "" , onValueChange = {}, label = {Text("Player 1")})

                Column (modifier = Modifier.verticalScroll(rememberScrollState()).padding(10.dp)){
                    Text("Team 1", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth(0.8f)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
                            Text("Player name", fontSize = 18.sp)
                            Surface(shape = CircleShape , modifier = Modifier.size(20.dp), color = Color.Gray) {  }
                        }
                    }




                }

            }
        }
    }

}