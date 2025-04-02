//package com.example.khelo
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.example.khelo.data.model.Match
//import com.example.khelo.data.storage.LocalStorage
//import com.example.khelo.ui.theme.PrimaryGreen
//import kotlinx.coroutines.launch
//import java.text.DecimalFormat
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LiveScoringScreen(
//    navController: NavHostController,
//    matchId: String
//) {
//    val context = LocalContext.current
//    val localStorage = LocalStorage.getInstance(context)
//    val coroutineScope = rememberCoroutineScope()
//
//    // State for match data
//    var match by remember { mutableStateOf<Match?>(null) }
//    var isLoading by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    // Scoring states
//    var runsToAdd by remember { mutableStateOf(0) }
//    var isWicket by remember { mutableStateOf(false) }
//    var isWide by remember { mutableStateOf(false) }
//    var isNoBall by remember { mutableStateOf(false) }
//    var isLegBye by remember { mutableStateOf(false) }
//    var isBye by remember { mutableStateOf(false) }
//
//    // Load match data
//    LaunchedEffect(matchId) {
//        try {
//            val loadedMatch = localStorage.getMatchById(matchId)
//            match = loadedMatch
//            isLoading = false
//        } catch (e: Exception) {
//            errorMessage = "Failed to load match: ${e.message}"
//            isLoading = false
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Live Scoring") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = PrimaryGreen,
//                    titleContentColor = Color.White,
//                    navigationIconContentColor = Color.White
//                )
//            )
//        }
//    ) { paddingValues ->
//        if (isLoading) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = PrimaryGreen)
//            }
//        } else if (errorMessage != null) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = errorMessage ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        } else if (match != null) {
//            val currentMatch = match!!
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                // Match header
//                MatchHeader(currentMatch)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Current innings info
//                CurrentInningsInfo(currentMatch)
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                // Scoring options
//                ScoringOptions(
//                    runsToAdd = runsToAdd,
//                    onRunsChange = { runsToAdd = it },
//                    isWicket = isWicket,
//                    onWicketChange = { isWicket = it },
//                    isWide = isWide,
//                    onWideChange = { isWide = it },
//                    isNoBall = isNoBall,
//                    onNoBallChange = { isNoBall = it },
//                    isLegBye = isLegBye,
//                    onLegByeChange = { isLegBye = it },
//                    isBye = isBye,
//                    onByeChange = { isBye = it }
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                // Submit button
//                Button(
//                    onClick = {
//                        coroutineScope.launch {
//                            val updatedMatch = updateMatchScore(
//                                currentMatch,
//                                runsToAdd,
//                                isWicket,
//                                isWide,
//                                isNoBall,
//                                isLegBye,
//                                isBye
//                            )
//
//                            localStorage.updateMatch(updatedMatch)
//                            match = updatedMatch
//
//                            // Reset states
//                            runsToAdd = 0
//                            isWicket = false
//                            isWide = false
//                            isNoBall = false
//                            isLegBye = false
//                            isBye = false
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = PrimaryGreen
//                    )
//                ) {
//                    Text(
//                        text = "Submit",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // End innings button (only show if first innings)
//                if (currentMatch.currentInnings == 1) {
//                    OutlinedButton(
//                        onClick = {
//                            coroutineScope.launch {
//                                val updatedMatch = currentMatch.copy(
//                                    currentInnings = 2
//                                )
//                                localStorage.updateMatch(updatedMatch)
//                                match = updatedMatch
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        border = BorderStroke(1.dp, PrimaryGreen),
//                        colors = ButtonDefaults.outlinedButtonColors(
//                            contentColor = PrimaryGreen
//                        )
//                    ) {
//                        Text(
//                            text = "End Innings",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                } else {
//                    // End match button
//                    OutlinedButton(
//                        onClick = {
//                            coroutineScope.launch {
//                                val updatedMatch = currentMatch.copy(
//                                    status = "completed"
//                                )
//                                localStorage.updateMatch(updatedMatch)
//                                navController.navigate("home") {
//                                    popUpTo("home") { inclusive = true }
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
//                        colors = ButtonDefaults.outlinedButtonColors(
//                            contentColor = MaterialTheme.colorScheme.error
//                        )
//                    ) {
//                        Text(
//                            text = "End Match",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MatchHeader(match: Match) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = PrimaryGreen.copy(alpha = 0.1f)
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "${match.team1Name} vs ${match.team2Name}",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = PrimaryGreen
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Overs: ${match.overs}",
//                fontSize = 16.sp,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        text = match.team1Name,
//                        fontWeight = FontWeight.Medium
//                    )
//                    Text(
//                        text = "${match.team1Score}/${match.team1Wickets}",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = PrimaryGreen
//                    )
//                }
//
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        text = match.team2Name,
//                        fontWeight = FontWeight.Medium
//                    )
//                    Text(
//                        text = "${match.team2Score}/${match.team2Wickets}",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = PrimaryGreen
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CurrentInningsInfo(match: Match) {
//    val currentInnings = match.currentInnings
//    val battingTeam = if (currentInnings == 1) match.team1Name else match.team2Name
//    val bowlingTeam = if (currentInnings == 1) match.team2Name else match.team1Name
//
//    val currentScore = if (currentInnings == 1) match.team1Score else match.team2Score
//    val currentWickets = if (currentInnings == 1) match.team1Wickets else match.team2Wickets
//
//    // Calculate current over
//    val totalBalls = if (currentInnings == 1) {
//        (match.team1Score / 6) + (match.team1Score % 6)
//    } else {
//        (match.team2Score / 6) + (match.team2Score % 6)
//    }
//
//    val overs = totalBalls / 6
//    val balls = totalBalls % 6
//    val currentOver = DecimalFormat("0.0").format(overs + (balls / 10.0))
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Current Innings: $currentInnings",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "$battingTeam batting",
//                fontSize = 16.sp,
//                color = PrimaryGreen
//            )
//
//            Text(
//                text = "$bowlingTeam bowling",
//                fontSize = 16.sp,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Score: $currentScore/$currentWickets",
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = "Overs: $currentOver",
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ScoringOptions(
//    runsToAdd: Int,
//    onRunsChange: (Int) -> Unit,
//    isWicket: Boolean,
//    onWicketChange: (Boolean) -> Unit,
//    isWide: Boolean,
//    onWideChange: (Boolean) -> Unit,
//    isNoBall: Boolean,
//    onNoBallChange: (Boolean) -> Unit,
//    isLegBye: Boolean,
//    onLegByeChange: (Boolean) -> Unit,
//    isBye: Boolean,
//    onByeChange: (Boolean) -> Unit
//) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(
//            text = "Scoring Options",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Runs buttons
//        Text(
//            text = "Runs",
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            for (runs in 0..6) {
//                RunButton(
//                    runs = runs,
//                    isSelected = runsToAdd == runs,
//                    onClick = { onRunsChange(runs) }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Extras options
//        Text(
//            text = "Extras",
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            ExtraButton(
//                text = "W",
//                isSelected = isWicket,
//                onClick = { onWicketChange(!isWicket) }
//            )
//
//            ExtraButton(
//                text = "WD",
//                isSelected = isWide,
//                onClick = { onWideChange(!isWide) }
//            )
//
//            ExtraButton(
//                text = "NB",
//                isSelected = isNoBall,
//                onClick = { onNoBallChange(!isNoBall) }
//            )
//
//            ExtraButton(
//                text = "LB",
//                isSelected = isLegBye,
//                onClick = { onLegByeChange(!isLegBye) }
//            )
//
//            ExtraButton(
//                text = "B",
//                isSelected = isBye,
//                onClick = { onByeChange(!isBye) }
//            )
//        }
//    }
//}
//
//@Composable
//fun RunButton(
//    runs: Int,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier.size(48.dp),
//        shape = CircleShape,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSelected) PrimaryGreen else Color.LightGray,
//            contentColor = if (isSelected) Color.White else Color.Black
//        )
//    ) {
//        Text(
//            text = runs.toString(),
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun ExtraButton(
//    text: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    OutlinedButton(
//        onClick = onClick,
//        modifier = Modifier.size(48.dp),
//        shape = CircleShape,
//        border = BorderStroke(1.dp, if (isSelected) PrimaryGreen else Color.LightGray),
//        colors = ButtonDefaults.outlinedButtonColors(
//            containerColor = if (isSelected) PrimaryGreen.copy(alpha = 0.1f) else Color.Transparent,
//            contentColor = if (isSelected) PrimaryGreen else Color.Black
//        )
//    ) {
//        Text(
//            text = text,
//            fontWeight = FontWeight.Bold,
//            fontSize = 12.sp
//        )
//    }
//}
//
//// Helper function to update match score
//private fun updateMatchScore(
//    match: Match,
//    runsToAdd: Int,
//    isWicket: Boolean,
//    isWide: Boolean,
//    isNoBall: Boolean,
//    isLegBye: Boolean,
//    isBye: Boolean
//): Match {
//    // Calculate total runs to add
//    var totalRuns = runsToAdd
//
//    // Add 1 run for wide or no ball
//    if (isWide || isNoBall) {
//        totalRuns += 1
//    }
//
//    // For leg byes and byes, the runs count to the team total but not to the batsman
//
//    // Update match based on current innings
//    return if (match.currentInnings == 1) {
//        match.copy(
//            team1Score = match.team1Score + totalRuns,
//            team1Wickets = if (isWicket) match.team1Wickets + 1 else match.team1Wickets
//        )
//    } else {
//        match.copy(
//            team2Score = match.team2Score + totalRuns,
//            team2Wickets = if (isWicket) match.team2Wickets + 1 else match.team2Wickets
//        )
//    }
//}
