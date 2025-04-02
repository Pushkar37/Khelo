package com.example.khelo

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.khelo.data.model.Match
import com.example.khelo.data.storage.LocalStorage
import com.example.khelo.ui.theme.PrimaryGreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val localStorage = LocalStorage.getInstance(context)
    
    // Check if user is logged in
    LaunchedEffect(Unit) {
        if (!localStorage.isLoggedIn()) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    var selectedItem by remember { mutableStateOf("Item 1") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    // Get live matches
    val liveMatchesState = remember { MutableStateFlow<List<Match>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        val liveMatches = localStorage.getLiveMatches()
        liveMatchesState.value = liveMatches
    }
    
    val liveMatches by liveMatchesState.asStateFlow().collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerPanel(navController, selectedItem, onItemClick = { selectedItem = it } ) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    drawerState,
                    scope
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("profile") },
                    containerColor = PrimaryGreen
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = androidx.compose.ui.graphics.Color.White
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Live Matches", fontSize = 20.sp)
                }

                // Live Match Display Cards
                if (liveMatches.isEmpty()) {
                    Text(
                        text = "No live matches at the moment",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        liveMatches.forEach { match ->
                            MatchDisplaySummeryCard(
                                team1Name = match.team1Name,
                                team2Name = match.team2Name,
                                team1Score = "${match.team1Score}/${match.team1Wickets}",
                                team2Score = "${match.team2Score}/${match.team2Wickets}",
                                overs = match.overs.toString(),
                                onClick = {
                                    // Navigate to LiveScoringScreen with the match ID
                                    navController.navigate("liveScoring/${match.matchId}")
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Your Recent Matches", fontSize = 20.sp)
                }
                
                // User's recent matches
                val currentUser = localStorage.getCurrentUser()
                val userMatches = currentUser?.let {
                    localStorage.getMatchesByUser(it.phoneNumber)
                } ?: emptyList()
                
                if (userMatches.isEmpty()) {
                    Text(
                        text = "You haven't created any matches yet",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        userMatches.take(5).forEach { match ->
                            MatchDisplaySummeryCard(
                                team1Name = match.team1Name,
                                team2Name = match.team2Name,
                                team1Score = "${match.team1Score}/${match.team1Wickets}",
                                team2Score = "${match.team2Score}/${match.team2Wickets}",
                                overs = match.overs.toString(),
                                onClick = {
                                    // If match is ongoing, navigate to LiveScoringScreen
                                    if (match.status == "ongoing") {
                                        navController.navigate("liveScoring/${match.matchId}")
                                    } else {
                                        // For completed matches, we could navigate to a match details screen
                                        // This could be implemented later
                                    }
                                }
                            )
                        }
                    }
                }

                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Articles", fontSize = 20.sp)
                }
                
                // Dummy Articles
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ArticleCard(
                        title = "IPL 2025: Top 5 Batsmen to Watch",
                        summary = "With the IPL 2025 season approaching, here are the top batsmen who could make a big impact this year.",
                        date = "April 1, 2025",
                        imageUrl = null
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    ArticleCard(
                        title = "India's New Bowling Sensation Takes 5 Wickets on Debut",
                        summary = "The young fast bowler impressed everyone with his pace and accuracy in his first international match.",
                        date = "March 28, 2025",
                        imageUrl = null
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    ArticleCard(
                        title = "Cricket World Cup 2026: Host Nations Announced",
                        summary = "ICC has announced the host nations for the upcoming Cricket World Cup scheduled for 2026.",
                        date = "March 25, 2025",
                        imageUrl = null
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Matches", fontSize = 20.sp)
                }
                
                // Dummy Matches
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    MatchDisplaySummeryCard(
                        team1Name = "India",
                        team2Name = "Australia",
                        team1Score = "287/6",
                        team2Score = "192/4",
                        overs = "42.3",
                        onClick = { /* No action */ }
                    )
                    
                    MatchDisplaySummeryCard(
                        team1Name = "England",
                        team2Name = "South Africa",
                        team1Score = "325/8",
                        team2Score = "201/10",
                        overs = "50.0",
                        onClick = { /* No action */ }
                    )
                    
                    MatchDisplaySummeryCard(
                        team1Name = "New Zealand",
                        team2Name = "Pakistan",
                        team1Score = "156/3",
                        team2Score = "155/10",
                        overs = "32.4",
                        onClick = { /* No action */ }
                    )
                    
                    MatchDisplaySummeryCard(
                        team1Name = "West Indies",
                        team2Name = "Sri Lanka",
                        team1Score = "203/7",
                        team2Score = "189/9",
                        overs = "50.0",
                        onClick = { /* No action */ }
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
