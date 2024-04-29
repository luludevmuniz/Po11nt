package presentation.game

import domain.model.Player
import domain.model.ServingSide

data class GameState(
    val playerOne: Player,
    val playerTwo: Player,
    val servingSide: ServingSide,
    val maxScore: Int,
    val maxSets: Int,
    var setEnded: Boolean = false,
    var showRestartModal: Boolean = false,
    var showRulesDialog: Boolean = false,
    var showPlayersDialog: Boolean = false,
    var showSummaryDialog: Boolean = false,
    var winner: Player? = null,
    var loser: Player = Player(),
    var switchSides: Boolean
)