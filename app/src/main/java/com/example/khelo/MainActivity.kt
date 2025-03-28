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
//        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(android.graphics.Color.red(23), android.graphics.Color.red(23)))
        setContent {
            MainScreen()
        }
    }
}

//@Composable
//fun test(){
//    var p : Player by rememberSaveable { mutableStateOf(Player("123","Ankit", "Ind" , 0, BattingStats(0,0,0,0,0.0f), BowlingStats(Overs(0,0),0,0.0f,0))) }
//}



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
        composable("MatchScoringScreen") {
            MatchScoringScreen(navController)
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
