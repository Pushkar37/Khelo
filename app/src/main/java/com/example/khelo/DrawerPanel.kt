package com.example.khelo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.khelo.data.model.Player
import com.example.khelo.data.storage.LocalStorage
import com.example.khelo.ui.theme.PrimaryGreen
import com.example.khelo.ui.theme.SecondaryGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerPanel(navController: NavHostController, selectedItem: String, onItemClick: (String) -> Unit) {

    val context = LocalContext.current
    val localStorage = LocalStorage.getInstance(context)

    // Get current user and player data
    val currentUser = localStorage.getCurrentUser()
    var player by remember { mutableStateOf<Player?>(null) }


    // Load player data
    LaunchedEffect(currentUser) {
        currentUser?.let {
            player = localStorage.getPlayerByPhone(it.phoneNumber)
        }
    }

    // Check if user is logged in
    if (currentUser == null) {
        // Redirect to login if not logged in
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo("profile") { inclusive = true }
            }
        }
        return
    }

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .background(PrimaryGreen.copy(alpha = .2f))
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(0.9f)

        ) {
            Spacer(Modifier.height(24.dp))
//            Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            ProfileCard(
                user = currentUser,
                player = player,
                navController
            )

            Text("Host", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Match") },
                icon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                selected = selectedItem == "Match",
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = {
                    onItemClick("Match")
                    navController.navigate(StartAMatchScreen.route)
                }
            )
            NavigationDrawerItem(
                label = { Text("Tournament") },
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                selected = selectedItem == "Tournament",
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = {
                    onItemClick("Tournament")
                    navController.navigate(StartATournamentScreen.route)
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

//            Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)

            NavigationDrawerItem(
                label = { Text("Live Matches") },
                selected = selectedItem == "Live Matches",
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Live Matches") }
            )
            NavigationDrawerItem(
                label = { Text("Schedule") },
                selected = selectedItem == "Schedule",
                icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Schedule") }
            )

            NavigationDrawerItem(
                label = { Text("Find a Player") },
                selected = selectedItem == "Find a Player",
                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Find a Player") }
            )
            NavigationDrawerItem(
                label = { Text("Store") },
                selected = selectedItem == "Store",
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                badge = { Text("2") }, // Placeholder
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = {
                    onItemClick("Store")
                    navController.navigate(ShopScreen.route)
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

//            Text("Section 3", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Settings") },
                selected = selectedItem == "Settings",
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                badge = { Text("20") }, // Placeholder
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Settings") }
            )
            NavigationDrawerItem(
                label = { Text("Help and feedback") },
                selected = selectedItem == "Help and Feedback",
                icon = { Icon(Icons.Default.Face, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Help and Feedback") }
            )

            NavigationDrawerItem(
                label = { Text("Share") },
                selected = selectedItem == "Share",
                icon = { Icon(Icons.Default.Share, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Share") }
            )
            NavigationDrawerItem(
                label = { Text("Rate Us") },
                selected = selectedItem == "Rate Us",
                icon = { Icon(Icons.Outlined.Star, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Rate Us") }
            )
            NavigationDrawerItem(
                label = { Text("Log out" , fontWeight = FontWeight.Bold , color = Color.Red) },
                selected = selectedItem == "Log out",
                icon = { Icon(Icons.AutoMirrored.Default.ExitToApp, contentDescription = null , tint = Color.Red) },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = PrimaryGreen.copy(alpha = 0f), selectedContainerColor = PrimaryGreen),
                onClick = { onItemClick("Log out")

                    localStorage.logoutUser()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun TopAppBar(drawerState: DrawerState, scope: CoroutineScope, navController: NavHostController){
    Card(shape = RectangleShape, elevation = CardDefaults.cardElevation(defaultElevation = 50.dp),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
            .background(SecondaryGreen)
//        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .background(SecondaryGreen)
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

            ) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon", tint = Color.White ,
                modifier = Modifier
                    .clickable(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }
                    )
                    .size(30.dp)
            )

//            Icon(painter = painterResource(R.drawable.khelo_logo), contentDescription = "Kheloo Icon", tint = Color.White ,
//                modifier = Modifier
//                    .clip(shape = RectangleShape)
////                    .size(30.dp)
//            )

            Text(
                text = "Kheloo",
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.9f)
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "person image",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
                    .clickable(onClick = { navController.navigate("profile") },)
            )
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController, selectedItem0: String) {

    var selectedItem by remember { mutableStateOf(selectedItem0) }

    Card(shape = RectangleShape, elevation = CardDefaults.cardElevation(defaultElevation = 50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryGreen)
            .shadow(10.dp, ambientColor = Color.Black, spotColor = Color.Black)
//        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .statusBarsPadding(),
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(SecondaryGreen)
                .padding(vertical = 20.dp, horizontal = 30.dp)
                .fillMaxWidth(),


        ) {
            Icon(Icons.Default.Home, contentDescription = "Home icon", tint = if (selectedItem == "HomeScreen") PrimaryGreen else Color.White,
                modifier = Modifier
                    .clickable(onClick = {
                        selectedItem = "HomeScreen"
                        navController.navigate(HomeScreen.route)
                    }
                    )
            )
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon" ,
                tint = if (selectedItem == "SearchScreen") PrimaryGreen else Color.White,
                modifier = Modifier.clickable(onClick = {
                    selectedItem = "SearchScreen"
                    navController.navigate(SearchScreen.route)
                }))

            Icon(Icons.Default.AddCircle, contentDescription = "Add icon",
                tint = if (selectedItem == "StartAMatchScreen") PrimaryGreen else Color.White,
                modifier = Modifier
                    .clickable(onClick = {
                        selectedItem = "StartAMatchScreen"
                        navController.navigate(StartAMatchScreen.route)
                    }
                    )
            )
            Icon(Icons.Default.AccountCircle, contentDescription = "Add icon",
                tint = if (selectedItem == "ProfileScreen") PrimaryGreen else Color.White,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            selectedItem = "ProfileScreen"
                            navController.navigate("profile") },))

            Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping Cart",
                tint = if (selectedItem == "ShoppingScreen") PrimaryGreen else Color.White,
                modifier = Modifier
                    .clickable(onClick = {
                        selectedItem = "ShoppingScreen"
                        navController.navigate(ShopScreen.route)
                    }
                    )
            )
        }
    }
}


//@Preview
//@Composable
//fun PreviewDrawerPanel(){
//    var selectedItem by remember { mutableStateOf("Item 1") }
//    DrawerPanel(selectedItem, onItemClick = { selectedItem = it })
//}


//@Preview(showBackground = true)
//@Composable
//fun TopAppBarPreview(){
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    TopAppBar(drawerState,scope)
//}

//
//@Composable
//@Preview
//fun PreviewBottomNavigationBar(){
//    BottomNavigationBar()
//}

