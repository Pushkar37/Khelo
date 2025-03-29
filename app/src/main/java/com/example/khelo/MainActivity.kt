package com.example.khelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeScreen.route) {
        composable(HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(ShopScreen.route) {
            ShopScreen(navController)
        }
        composable(StartAMatchScreen.route) {
            StartAMatchScreen(navController)
        }
        composable(
            "StartAMatchScreen2?team1Name={team1Name}&team2Name={team2Name}",
            arguments = listOf(
                navArgument("team1Name") { type = NavType.StringType },
                navArgument("team2Name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val team1Name = backStackEntry.arguments?.getString("team1Name") ?: "Team 1"
            val team2Name = backStackEntry.arguments?.getString("team2Name") ?: "Team 2"
            StartAMatchScreen2(navController, team1Name, team2Name)
        }
        composable(
            "TossAndPlaying11Screen?team1Players={team1Players}&team2Players={team2Players}&team1Name={team1Name}&team2Name={team2Name}",
            arguments = listOf(
                navArgument("team1Players") { type = NavType.StringType },
                navArgument("team2Players") { type = NavType.StringType },
                navArgument("team1Name") { type = NavType.StringType },
                navArgument("team2Name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val team1Players = backStackEntry.arguments?.getString("team1Players")
                ?.split(",") ?: emptyList()
            val team2Players = backStackEntry.arguments?.getString("team2Players")
                ?.split(",") ?: emptyList()
            val team1Name = backStackEntry.arguments?.getString("team1Name") ?: "Team 1"
            val team2Name = backStackEntry.arguments?.getString("team2Name") ?: "Team 2"
            TossAndPlaying11Screen(navController, team1Players, team2Players, team1Name, team2Name)
        }
        composable(
            "OpeningPlayersScreen?team1Name={team1Name}&team2Name={team2Name}&tossWinner={tossWinner}&tossDecision={tossDecision}&team1Players={team1Players}&team2Players={team2Players}&team1Captain={team1Captain}&team1ViceCaptain={team1ViceCaptain}&team2Captain={team2Captain}&team2ViceCaptain={team2ViceCaptain}",
            arguments = listOf(
                navArgument("team1Name") { type = NavType.StringType },
                navArgument("team2Name") { type = NavType.StringType },
                navArgument("tossWinner") { type = NavType.StringType },
                navArgument("tossDecision") { type = NavType.StringType },
                navArgument("team1Players") { type = NavType.StringType },
                navArgument("team2Players") { type = NavType.StringType },
                navArgument("team1Captain") { type = NavType.StringType },
                navArgument("team1ViceCaptain") { type = NavType.StringType },
                navArgument("team2Captain") { type = NavType.StringType },
                navArgument("team2ViceCaptain") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val team1Name = backStackEntry.arguments?.getString("team1Name") ?: ""
            val team2Name = backStackEntry.arguments?.getString("team2Name") ?: ""
            val tossWinner = backStackEntry.arguments?.getString("tossWinner") ?: ""
            val tossDecision = backStackEntry.arguments?.getString("tossDecision") ?: ""
            val team1Players = backStackEntry.arguments?.getString("team1Players")?.split(",") ?: emptyList()
            val team2Players = backStackEntry.arguments?.getString("team2Players")?.split(",") ?: emptyList()
            val team1Captain = backStackEntry.arguments?.getString("team1Captain") ?: ""
            val team1ViceCaptain = backStackEntry.arguments?.getString("team1ViceCaptain") ?: ""
            val team2Captain = backStackEntry.arguments?.getString("team2Captain") ?: ""
            val team2ViceCaptain = backStackEntry.arguments?.getString("team2ViceCaptain") ?: ""
            
            OpeningPlayersScreen(
                navController = navController,
                team1Name = team1Name,
                team2Name = team2Name,
                tossWinner = tossWinner,
                tossDecision = tossDecision,
                team1Players = team1Players,
                team2Players = team2Players,
                team1Captain = team1Captain,
                team1ViceCaptain = team1ViceCaptain,
                team2Captain = team2Captain,
                team2ViceCaptain = team2ViceCaptain
            )
        }
        composable(
            "MatchScoringScreen?team1Name={team1Name}&team2Name={team2Name}&tossWinner={tossWinner}&tossDecision={tossDecision}&openingBatsman1={openingBatsman1}&openingBatsman2={openingBatsman2}&openingBowler={openingBowler}&wicketKeeper={wicketKeeper}&battingTeam={battingTeam}&bowlingTeam={bowlingTeam}",
            arguments = listOf(
                navArgument("team1Name") { type = NavType.StringType },
                navArgument("team2Name") { type = NavType.StringType },
                navArgument("tossWinner") { type = NavType.StringType },
                navArgument("tossDecision") { type = NavType.StringType },
                navArgument("openingBatsman1") { type = NavType.StringType },
                navArgument("openingBatsman2") { type = NavType.StringType },
                navArgument("openingBowler") { type = NavType.StringType },
                navArgument("wicketKeeper") { type = NavType.StringType },
                navArgument("battingTeam") { type = NavType.StringType },
                navArgument("bowlingTeam") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val team1Name = backStackEntry.arguments?.getString("team1Name") ?: ""
            val team2Name = backStackEntry.arguments?.getString("team2Name") ?: ""
            val tossWinner = backStackEntry.arguments?.getString("tossWinner") ?: ""
            val tossDecision = backStackEntry.arguments?.getString("tossDecision") ?: ""
            val openingBatsman1 = backStackEntry.arguments?.getString("openingBatsman1") ?: ""
            val openingBatsman2 = backStackEntry.arguments?.getString("openingBatsman2") ?: ""
            val openingBowler = backStackEntry.arguments?.getString("openingBowler") ?: ""
            val wicketKeeper = backStackEntry.arguments?.getString("wicketKeeper") ?: ""
            val battingTeam = backStackEntry.arguments?.getString("battingTeam") ?: ""
            val bowlingTeam = backStackEntry.arguments?.getString("bowlingTeam") ?: ""
            
            MatchScoringScreen(
                navController = navController,
                team1Name = team1Name,
                team2Name = team2Name,
                tossWinner = tossWinner,
                tossDecision = tossDecision,
                openingBatsman1 = openingBatsman1,
                openingBatsman2 = openingBatsman2,
                openingBowler = openingBowler,
                wicketKeeper = wicketKeeper,
                battingTeam = battingTeam,
                bowlingTeam = bowlingTeam
            )
        }
        composable(StartATournamentScreen.route) {
            StartATournamentScreen(navController)
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen(){
    MainScreen()
}
