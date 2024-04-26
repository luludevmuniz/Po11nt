package navigation

sealed class Screen(val route: String) {
    data object Start: Screen("/start")
    data object Players: Screen("/players")
    data object Rules: Screen("/rules")
    data object Game: Screen("/game")
}