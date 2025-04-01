package com.example.khelo

interface Destinations{
    val route: String
}

object HomeScreen: Destinations{
    override val route: String = "HomeScreen"
}

object StartAMatchScreen: Destinations{
    override val route: String = "StartAMatchScreen"
}
object StartAMatchScreen2: Destinations{
    override val route: String = "StartAMatchScreen2"
}

object MatchScoringScreen: Destinations{
    override val route: String = "MatchScoringScreen"
}

object CaptainSelectionScreen: Destinations{
    override val route: String = "CaptainSelectionScreen"
}

object StartATournamentScreen: Destinations{
    override val route: String = "StartATournament"
}
object LiveMatchesScreen: Destinations{
    override val route: String = "LiveMatches"
}
object ScheduleScreen: Destinations{
    override val route: String = "Schedule"
}
object SearchScreen: Destinations{
    override val route: String = "SearchScreen"
}
object ShopScreen: Destinations{
    override val route: String = "ShopScreen"
}
