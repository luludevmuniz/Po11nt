package presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import domain.model.Player
import domain.model.ServingSide
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import presentation.game.GameEvent.*
import presentation.game.dialog.*
import utils.Orientation

@Composable
fun GameScreen(
    orientation: Orientation,
    playerOne: Player,
    playerTwo: Player,
    initalMaxSets: Int,
    initalMaxScore: Int,
    startServingSide: ServingSide,
    onFinishClicked: () -> Unit
) {
    val viewModel = viewModel(GameViewModel::class, listOf(3)) {
        GameViewModel(
            playerOne = playerOne,
            playerTwo = playerTwo,
            maxScore = initalMaxScore,
            maxSets = initalMaxSets,
            startServingSide = startServingSide
        )
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val shouldRollback = remember { mutableStateOf(Pair(false, false)) }

    Scaffold { paddingValues ->
        GameContent(
            playerOne = uiState.value.playerOne,
            playerTwo = uiState.value.playerTwo,
            maxScore = uiState.value.maxScore,
            servingSide = uiState.value.servingSide,
            shouldRollback = shouldRollback,
            onShowRestartModal = {
                viewModel.onEvent(OnShowRestartModal)
            },
            onShowPlayersDialog = {
                viewModel.onEvent(OnShowPlayersDialog)
            },
            onShowRulesDialog = {
                viewModel.onEvent(OnShowRulesDialog)
            },
            onPlayerOneScored = {
                viewModel.onEvent(
                    OnPlayerOneScoreChange(uiState.value.playerOne.score.plus(1))
                )
            },
            onPlayerTwoScored = {
                viewModel.onEvent(
                    OnPlayerTwoScoreChange(uiState.value.playerTwo.score.plus(1))
                )
            },
            onPlayerOneScoreRollback = {
                viewModel.onEvent(
                    OnPlayerOneScoreChange(uiState.value.playerOne.score.minus(1))
                )
            },
            onPlayerTwoScoreRollback = {
                viewModel.onEvent(
                    OnPlayerTwoScoreChange(uiState.value.playerTwo.score.minus(1))
                )
            }
        ) { centerContent,
            playerOneScoreField,
            playerTwoScoreField ->
            when (orientation) {
                Orientation.Horizontal -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(paddingValues)
                    ) {
                        playerOneScoreField(0.25f)
                        Surface(modifier = Modifier.weight(0.5f)) {
                            centerContent(Modifier.fillMaxHeight())
                        }
                        playerTwoScoreField(0.25f)
                    }
                }

                Orientation.Vertical -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(paddingValues)
                    ) {
                        centerContent(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f)
                        )

                        Row(Modifier.fillMaxWidth()) {
                            playerOneScoreField(0.5f)
                            playerTwoScoreField(0.5f)
                        }
                    }
                }
            }
        }

        if (uiState.value.setEnded) {
            SetEndDialog(
                title = "Fim do ${(playerOne.sets + playerTwo.sets).plus(1)}º set",
                onContinueClicked = {
                    viewModel.onEvent(OnStartNextSet)
                },
                onEndGameClicked = {
                    viewModel.onEvent(OnFinishGame)
                },
                onDismissRequest = {
                    viewModel.onEvent(OnStartNextSet)
                },
            )
        }

        if (uiState.value.showSummaryDialog) {
            if (uiState.value.winner != null) {
                SummaryDialog(
                    winner = uiState.value.winner!!,
                    loser = uiState.value.loser
                ) {
                    viewModel.onEvent(OnCloseSummaryDialog)
                    onFinishClicked()
                }
            } else {
                onFinishClicked()
            }
        }

        if (uiState.value.showPlayersDialog) {
            PlayersDialog(
                orientation = orientation,
                playerOneName = uiState.value.playerOne.name,
                playerTwoName = uiState.value.playerTwo.name,
                onSaveClick = { playerOneName, playerTwoName ->
                    viewModel.onEvent(OnPlayerOneNameChange(playerOneName))
                    viewModel.onEvent(OnPlayerTwoNameChange(playerTwoName))
                    viewModel.onEvent(OnClosePlayersDialog)
                },
                onDismissRequest = {
                    viewModel.onEvent(OnClosePlayersDialog)
                }
            )
        }

        if (uiState.value.showRulesDialog) {
            RulesDialog(
                orientation = orientation,
                players = listOf(
                    uiState.value.playerOne,
                    uiState.value.playerTwo
                ),
                maxSets = uiState.value.maxSets,
                maxScore = uiState.value.maxScore,
                servingSide = uiState.value.servingSide,
                onDismissRequest = {
                    viewModel.onEvent(OnCloseRulesDialog)
                },
                onSaveClick = { sets, score, servingSide ->
                    viewModel.onEvent(OnMaxSetsChange(sets))
                    viewModel.onEvent(OnMaxScoreChange(score))
                    viewModel.onEvent(OnServingSideChange(servingSide))
                    viewModel.onEvent(OnCloseRulesDialog)
                }
            )
        }

        if (uiState.value.showRestartModal) {
            RestartGameModal(
                onRestartClicked = {
                    viewModel.onEvent(OnRestartGame)
                    viewModel.onEvent(OnCloseRestartModal)
                },
                onFinishClicked = {
                    viewModel.onEvent(OnCloseRestartModal)
                    viewModel.onEvent(OnFinishGame)
                },
                onDismissRequest = {
                    viewModel.onEvent(OnCloseRestartModal)
                }
            )
        }
    }
}