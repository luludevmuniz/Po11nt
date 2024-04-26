package presentation.players

sealed interface PlayersEvent {
    data class OnPlayerOneNameChanged(val value: String): PlayersEvent
    data class OnPlayerTwoNameChanged(val value: String): PlayersEvent
}