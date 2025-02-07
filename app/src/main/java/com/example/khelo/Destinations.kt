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
object StartATournamentScreen: Destinations{
    override val route: String = "StartATournament"
}
object LiveMatchesScreen: Destinations{
    override val route: String = "LiveMatches"
}
object ScheduleScreen: Destinations{
    override val route: String = "Schedule"
}
object FindAPlayerScreen: Destinations{
    override val route: String = "Schedule"
}
object ShopScreen: Destinations{
    override val route: String = "ShopScreen"
}


