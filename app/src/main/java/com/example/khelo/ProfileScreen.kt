package com.example.khelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.khelo.data.model.Player
import com.example.khelo.data.model.User
import com.example.khelo.data.storage.LocalStorage
import com.example.khelo.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            ProfileHeader(user = currentUser, player = player)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stats Cards
            PlayerStatsSection(player = player)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Logout Button
            Button(
                onClick = {
                    localStorage.logoutUser()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun ProfileHeader(user: User, player: Player?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(PrimaryGreen.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(80.dp),
                tint = PrimaryGreen
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User Name
        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        // Phone Number
        Text(
            text = user.phoneNumber,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        
        // Email
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun PlayerStatsSection(player: Player?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Cricket Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Stats Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard(
                title = "Matches",
                value = player?.totalMatches?.toString() ?: "0",
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            StatCard(
                title = "Runs",
                value = player?.totalRuns?.toString() ?: "0",
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard(
                title = "Wickets",
                value = player?.totalWickets?.toString() ?: "0",
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            StatCard(
                title = "Catches",
                value = player?.totalCatches?.toString() ?: "0",
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Batting Average
        val battingAverage = if ((player?.totalMatches ?: 0) > 0 && (player?.totalRuns ?: 0) > 0) {
            String.format("%.2f", player!!.totalRuns.toFloat() / player.totalMatches.toFloat())
        } else {
            "0.00"
        }
        
        StatCard(
            title = "Batting Average",
            value = battingAverage,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
