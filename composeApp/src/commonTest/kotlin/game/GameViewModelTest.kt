package game

import domain.model.Player
import domain.model.ServingSide.Left
import domain.model.ServingSide.Right
import presentation.game.GameEvent
import presentation.game.GameEvent.OnClosePlayersDialog
import presentation.game.GameEvent.OnCloseRestartModal
import presentation.game.GameEvent.OnMaxScoreChange
import presentation.game.GameEvent.OnMaxSetsChange
import presentation.game.GameEvent.OnPlayerOneNameChange
import presentation.game.GameEvent.OnPlayerOneScoreChange
import presentation.game.GameEvent.OnPlayerTwoNameChange
import presentation.game.GameEvent.OnPlayerTwoScoreChange
import presentation.game.GameEvent.OnRestartGame
import presentation.game.GameEvent.OnServingSideChange
import presentation.game.GameEvent.OnShowPlayersDialog
import presentation.game.GameEvent.OnShowRestartModal
import presentation.game.GameEvent.OnShowRulesDialog
import presentation.game.GameEvent.OnStartNextSet
import presentation.game.GameViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameViewModelTest {
    private val maxScore = 11
    private val maxSets = 3
    private val playerOne = Player(name = "Lucas")
    private val playerTwo = Player(name = "Gustavo")
    private val startServingSide = Left

    private val viewModel = GameViewModel(
        playerOne = playerOne,
        playerTwo = playerTwo,
        startServingSide = startServingSide,
        maxScore = maxScore,
        maxSets = maxSets
    )

    @Test
    fun shouldChangeMaxScore() {
        viewModel.onEvent(OnMaxScoreChange(maxScore.plus(1)))
        assertEquals(maxScore.plus(1), viewModel.uiState.value.maxScore)
    }

    @Test
    fun shouldChangeMaxSets() {
        viewModel.onEvent(OnMaxSetsChange(maxSets.plus(1)))
        assertEquals(maxSets.plus(1), viewModel.uiState.value.maxSets)
    }

    @Test
    fun shouldChangePlayerOneName() {
        val newPlayerOneName = "Fabrício"
        viewModel.onEvent(OnPlayerOneNameChange(newPlayerOneName))
        assertEquals(newPlayerOneName, viewModel.uiState.value.playerOne.name)
    }

    @Test
    fun shouldChangePlayerTwoName() {
        val newPlayerTwoName = "Michel"
        viewModel.onEvent(OnPlayerTwoNameChange(newPlayerTwoName))
        assertEquals(newPlayerTwoName, viewModel.uiState.value.playerTwo.name)
    }

    @Test
    fun shouldChangePlayerOneScore() {
        val playerOneNewScore = 5
        viewModel.onEvent(OnPlayerOneScoreChange(playerOneNewScore))
        assertEquals(playerOneNewScore, viewModel.uiState.value.playerOne.score)
    }

    @Test
    fun shouldChangePlayerTwoScore() {
        val playerTwoNewScore = 5
        viewModel.onEvent(OnPlayerTwoScoreChange(playerTwoNewScore))
        assertEquals(playerTwoNewScore, viewModel.uiState.value.playerTwo.score)
    }

    @Test
    fun shouldChangeServingSide() {
        val newServingSide = Right
        viewModel.onEvent(OnServingSideChange(newServingSide))
        assertEquals(newServingSide, viewModel.uiState.value.servingSide)
    }

    @Test
    fun shouldHideSetSummaryDialog() {
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        assertTrue(viewModel.uiState.value.setEnded)
        viewModel.onEvent(OnStartNextSet)
        assertFalse(viewModel.uiState.value.setEnded)
    }

    @Test
    fun shouldChangeStartingServingSideInEverySet() {
        viewModel.onEvent(OnStartNextSet)
        assertEquals(Right, viewModel.uiState.value.servingSide)
        viewModel.onEvent(OnStartNextSet)
        assertEquals(Left, viewModel.uiState.value.servingSide)
        viewModel.onEvent(OnStartNextSet)
        assertEquals(Right, viewModel.uiState.value.servingSide)
    }

    @Test
    fun shouldShowSummaryDialogIfPlayerWinsTheGame() {
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        assertTrue(viewModel.uiState.value.showSummaryDialog)
    }

    @Test
    fun shouldPlayerOneWinTheGame() {
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        assertEquals(viewModel.uiState.value.playerOne, viewModel.uiState.value.winner)
    }

    @Test
    fun shouldPlayerTwoWinTheGame() {
        viewModel.onEvent(OnPlayerTwoScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        viewModel.onEvent(OnPlayerTwoScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        assertEquals(viewModel.uiState.value.playerTwo, viewModel.uiState.value.winner)
    }

    @Test
    fun shouldShowRestartModal() {
        viewModel.onEvent(OnShowRestartModal)
        assertTrue(viewModel.uiState.value.showRestartModal)
    }

    @Test
    fun shouldCloseRestartModal() {
        viewModel.onEvent(OnCloseRestartModal)
        assertFalse(viewModel.uiState.value.showRestartModal)
    }

    @Test
    fun shouldShowPlayersDialog() {
        viewModel.onEvent(OnShowPlayersDialog)
        assertTrue(viewModel.uiState.value.showPlayersDialog)
    }

    @Test
    fun shouldClosePlayersDialog() {
        viewModel.onEvent(OnClosePlayersDialog)
        assertFalse(viewModel.uiState.value.showPlayersDialog)
    }

    @Test
    fun shouldShowRulesDialog() {
        viewModel.onEvent(OnShowRulesDialog)
        assertTrue(viewModel.uiState.value.showRulesDialog)
    }

    @Test
    fun shouldCloseRulesDialog() {
        viewModel.onEvent(GameEvent.OnCloseRulesDialog)
        assertFalse(viewModel.uiState.value.showRulesDialog)
    }

    @Test
    fun shouldRestartSet() {
        viewModel.onEvent(OnPlayerOneScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        viewModel.onEvent(OnPlayerTwoScoreChange(maxScore))
        viewModel.onEvent(OnStartNextSet)
        viewModel.onEvent(OnPlayerOneScoreChange(8))
        viewModel.onEvent(OnPlayerTwoScoreChange(5))
        viewModel.onEvent(OnRestartGame)
        assertEquals(0, viewModel.uiState.value.playerOne.score)
        assertEquals(0, viewModel.uiState.value.playerOne.sets)
        assertEquals(0, viewModel.uiState.value.playerTwo.score)
        assertEquals(0, viewModel.uiState.value.playerTwo.sets)
    }

    @Test
    fun shouldBeDeuce() {
        viewModel.onEvent(OnPlayerOneScoreChange(10))
        viewModel.onEvent(OnPlayerTwoScoreChange(10))
        assertEquals(maxScore.plus(1), viewModel.uiState.value.maxScore)
    }
}