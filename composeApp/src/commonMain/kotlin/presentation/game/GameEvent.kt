package presentation.game

import domain.model.ServingSide

sealed interface GameEvent {
    data class OnPlayerOneScoreChange(val value: Int) : GameEvent
    data class OnPlayerTwoScoreChange(val value: Int) : GameEvent
    data class OnPlayerOneNameChange(val value: String) : GameEvent
    data class OnPlayerTwoNameChange(val value: String) : GameEvent
    data class OnMaxSetsChange(val value: Int) : GameEvent
    data class OnMaxScoreChange(val value: Int) : GameEvent
    data class OnServingSideChange(val value: ServingSide) : GameEvent
    data object OnStartNextSet : GameEvent
    data object OnFinishGame : GameEvent
    data object OnShowRestartModal: GameEvent
    data object OnCloseRestartModal: GameEvent
    data object OnShowRulesDialog: GameEvent
    data object OnCloseRulesDialog: GameEvent
    data object OnShowPlayersDialog: GameEvent
    data object OnClosePlayersDialog: GameEvent
    data object OnRestartGame: GameEvent
    data object OnCloseSummaryDialog: GameEvent
    data object OnSwitchPlayersSides: GameEvent
}