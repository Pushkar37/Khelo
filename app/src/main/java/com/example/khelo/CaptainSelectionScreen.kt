package com.example.khelo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.khelo.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptainSelectionScreen(
    navController: NavHostController,
    team1Players: List<String> = emptyList(),
    team2Players: List<String> = emptyList(),
    team1Name: String? = null,
    team2Name: String? = null,
    tossWinner: String? = null,
    tossDecision: String? = null
) {
    // Use safe defaults for team names
    val team1NameSafe = team1Name ?: "Team 1"
    val team2NameSafe = team2Name ?: "Team 2"
    
    var team1Captain by remember { mutableStateOf("") }
    var team1ViceCaptain by remember { mutableStateOf("") }
    var team2Captain by remember { mutableStateOf("") }
    var team2ViceCaptain by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Captains") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Team 1 Captain Selection
            Text(
                text = "${team1NameSafe} Captain",
                style = MaterialTheme.typography.titleMedium
            )
            
            var team1CaptainExpanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = team1CaptainExpanded,
                onExpandedChange = { team1CaptainExpanded = it }
            ) {
                OutlinedTextField(
                    value = team1Captain,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Captain") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = team1CaptainExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = team1CaptainExpanded,
                    onDismissRequest = { team1CaptainExpanded = false }
                ) {
                    team1Players.forEach { player ->
                        DropdownMenuItem(
                            text = { Text(player) },
                            onClick = {
                                team1Captain = player
                                team1CaptainExpanded = false
                            },
                            colors = MenuItemColors(
                                textColor = if (team1Captain == player) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Team 1 Vice-Captain Selection
            Text(
                text = "${team1NameSafe} Vice-Captain",
                style = MaterialTheme.typography.titleMedium
            )

            var team1ViceCaptainExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = team1ViceCaptainExpanded,
                onExpandedChange = { team1ViceCaptainExpanded = it }
            ) {
                OutlinedTextField(
                    value = team1ViceCaptain,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Vice-Captain") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = team1ViceCaptainExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = team1ViceCaptainExpanded,
                    onDismissRequest = { team1ViceCaptainExpanded = false }
                ) {
                    team1Players
                        .filter { it != team1Captain }
                        .forEach { player ->
                            DropdownMenuItem(
                                text = { Text(player) },
                                onClick = {
                                    team1ViceCaptain = player
                                    team1ViceCaptainExpanded = false
                                },
                                colors = MenuItemColors(
                                    textColor = if (team1ViceCaptain == player) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                    trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                }
            }

            // Team 2 Captain Selection
            Text(
                text = "${team2NameSafe} Captain",
                style = MaterialTheme.typography.titleMedium
            )

            var team2CaptainExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = team2CaptainExpanded,
                onExpandedChange = { team2CaptainExpanded = it }
            ) {
                OutlinedTextField(
                    value = team2Captain,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Captain") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = team2CaptainExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = team2CaptainExpanded,
                    onDismissRequest = { team2CaptainExpanded = false }
                ) {
                    team2Players.forEach { player ->
                        DropdownMenuItem(
                            text = { Text(player) },
                            onClick = {
                                team2Captain = player
                                team2CaptainExpanded = false
                            },
                            colors = MenuItemColors(
                                textColor = if (team2Captain == player) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Team 2 Vice-Captain Selection
            Text(
                text = "${team2NameSafe} Vice-Captain",
                style = MaterialTheme.typography.titleMedium
            )

            var team2ViceCaptainExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = team2ViceCaptainExpanded,
                onExpandedChange = { team2ViceCaptainExpanded = it }
            ) {
                OutlinedTextField(
                    value = team2ViceCaptain,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Vice-Captain") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = team2ViceCaptainExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = team2ViceCaptainExpanded,
                    onDismissRequest = { team2ViceCaptainExpanded = false }
                ) {
                    team2Players
                        .filter { it != team2Captain }
                        .forEach { player ->
                            DropdownMenuItem(
                                text = { Text(player) },
                                onClick = {
                                    team2ViceCaptain = player
                                    team2ViceCaptainExpanded = false
                                },
                                colors = MenuItemColors(
                                    textColor = if (team2ViceCaptain == player) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                    trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Continue button
            Button(
                onClick = {
                    if (team1Captain.isNotBlank() && team1ViceCaptain.isNotBlank() &&
                        team2Captain.isNotBlank() && team2ViceCaptain.isNotBlank()) {
                        // Navigate to the next screen with all the match information
                        navController.navigate(
                            "LineupSelectionScreen?team1Players=${team1Players.joinToString(",")}" +
                            "&team2Players=${team2Players.joinToString(",")}" +
                            "&team1Name=${team1NameSafe}&team2Name=${team2NameSafe}" +
                            "&tossWinner=${tossWinner ?: ""}&tossDecision=${tossDecision ?: ""}" +
                            "&team1Captain=${team1Captain}&team1ViceCaptain=${team1ViceCaptain}" +
                            "&team2Captain=${team2Captain}&team2ViceCaptain=${team2ViceCaptain}"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(PrimaryGreen)
            ) {
                Text("Next", fontWeight = FontWeight.Medium)
            }
        }
    }
}
