package game

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import domain.model.Player
import domain.model.ServingSide
import presentation.game.GameEvent
import presentation.game.GameScreen
import presentation.game.GameViewModel
import utils.Orientation
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class GameScreenUiTest {
    private lateinit var viewModel: GameViewModel
    private val maxScore = 11
    private val maxSets = 3
    private val playerOne = Player(name = "Lucas")
    private val playerTwo = Player(name = "Gustavo")
    private val startServingSide = ServingSide.Left

    private fun ComposeUiTest.setUpGameScreen() {
        setContent {
            GameScreen(
                uiState = viewModel.uiState.collectAsState().value,
                orientation = Orientation.Vertical,
                onFinishClicked = {
                },
                onShowRestartModal = {
                    viewModel.onEvent(GameEvent.OnShowRestartModal)
                },
                onShowPlayersDialog = {
                    viewModel.onEvent(GameEvent.OnShowPlayersDialog)
                },
                onShowRulesDialog = {
                    viewModel.onEvent(GameEvent.OnShowRulesDialog)
                },
                onPlayerOneScored = {
                    viewModel.onEvent(
                        GameEvent.OnPlayerOneScoreChange(
                            viewModel.uiState.value.playerOne.score.plus(
                                1
                            )
                        )
                    )
                },
                onPlayerTwoScored = {
                    viewModel.onEvent(
                        GameEvent.OnPlayerTwoScoreChange(
                            viewModel.uiState.value.playerTwo.score.plus(
                                1
                            )
                        )
                    )
                },
                onPlayerOneScoreRollback = {
                    viewModel.onEvent(
                        GameEvent.OnPlayerOneScoreChange(
                            viewModel.uiState.value.playerOne.score.minus(
                                1
                            )
                        )
                    )
                },
                onPlayerTwoScoreRollback = {
                    viewModel.onEvent(
                        GameEvent.OnPlayerTwoScoreChange(
                            viewModel.uiState.value.playerTwo.score.minus(
                                1
                            )
                        )
                    )
                },
                onStartNextSet = {
                    viewModel.onEvent(GameEvent.OnStartNextSet)
                },
                onEndGameClicked = {
                    viewModel.onEvent(GameEvent.OnFinishGame)
                },
                onCloseEndDialog = {
                    viewModel.onEvent(GameEvent.OnStartNextSet)
                },
                onCloseSummaryDialog = {
                    viewModel.onEvent(GameEvent.OnCloseSummaryDialog)
                },
                onChangePlayersNames = { playerOneName, playerTwoName ->
                    viewModel.onEvent(GameEvent.OnPlayerOneNameChange(playerOneName))
                    viewModel.onEvent(GameEvent.OnPlayerTwoNameChange(playerTwoName))
                    viewModel.onEvent(GameEvent.OnClosePlayersDialog)
                },
                onClosePlayersDialog = {
                    viewModel.onEvent(GameEvent.OnClosePlayersDialog)
                },
                onChangeRules = { sets, score, servingSide ->
                    viewModel.onEvent(GameEvent.OnMaxSetsChange(sets))
                    viewModel.onEvent(GameEvent.OnMaxScoreChange(score))
                    viewModel.onEvent(GameEvent.OnServingSideChange(servingSide))
                    viewModel.onEvent(GameEvent.OnCloseRulesDialog)
                },
                onCloseRulesDialog = {
                    viewModel.onEvent(GameEvent.OnCloseRulesDialog)
                },
                onRestartGameClicked = {
                    viewModel.onEvent(GameEvent.OnRestartGame)
                    viewModel.onEvent(GameEvent.OnCloseRestartModal)
                },
                onCloseRestartModal = {
                    viewModel.onEvent(GameEvent.OnCloseRestartModal)
                },
                onSwitchButtonClick = {}
            )
        }
    }

    @BeforeTest
    fun setUpViewModel() {
        viewModel = GameViewModel(
            playerOne = playerOne,
            playerTwo = playerTwo,
            startServingSide = startServingSide,
            maxScore = maxScore,
            maxSets = maxSets
        )
    }

    @Test
    fun shouldAllComponentsBeVisible() = runComposeUiTest {
        setUpGameScreen()
    }
}