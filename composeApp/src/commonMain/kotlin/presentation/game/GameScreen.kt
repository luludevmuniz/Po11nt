package presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import domain.model.ServingSide
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.set_summary
import presentation.game.dialog.PlayersDialog
import presentation.game.dialog.RestartGameModal
import presentation.game.dialog.RulesDialog
import presentation.game.dialog.SetEndDialog
import presentation.game.dialog.SummaryDialog
import utils.Orientation

@Composable
fun GameScreen(
    uiState: GameState,
    orientation: Orientation,
    onFinishClicked: () -> Unit,
    onPlayerOneScored: () -> Unit,
    onPlayerTwoScored: () -> Unit,
    onPlayerOneScoreRollback: () -> Unit,
    onPlayerTwoScoreRollback: () -> Unit,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit,
    onStartNextSet: () -> Unit,
    onEndGameClicked: () -> Unit,
    onCloseEndDialog: () -> Unit,
    onCloseSummaryDialog: () -> Unit,
    onChangePlayersNames: (String, String) -> Unit,
    onClosePlayersDialog: () -> Unit,
    onChangeRules: (
        maxSets: Int,
        maxScore: Int,
        servingSide: ServingSide
    ) -> Unit,
    onCloseRulesDialog: () -> Unit,
    onRestartGameClicked: () -> Unit,
    onCloseRestartModal: () -> Unit,
    onSwitchButtonClick: () -> Unit
) {
    val shouldRollback = remember {
        mutableStateOf(
            Pair(
                false,
                false
            )
        )
    }
    Scaffold { paddingValues ->
        GameContent(
            playerOne = uiState.playerOne,
            playerTwo = uiState.playerTwo,
            maxScore = uiState.maxScore,
            servingSide = uiState.servingSide,
            shouldRollback = shouldRollback,
            switchSides = uiState.switchSides,
            onShowRestartModal = onShowRestartModal,
            onShowPlayersDialog = onShowPlayersDialog,
            onShowRulesDialog = onShowRulesDialog,
            onPlayerOneScored = onPlayerOneScored,
            onPlayerTwoScored = onPlayerTwoScored,
            onPlayerOneScoreRollback = onPlayerOneScoreRollback,
            onPlayerTwoScoreRollback = onPlayerTwoScoreRollback,
            onSwitchButtonClick = onSwitchButtonClick
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

        if (uiState.setEnded) {
            SetEndDialog(
                title = stringResource(
                    Res.string.set_summary,
                    uiState.playerOne.sets + uiState.playerTwo.sets
                ),
                onContinueClicked = onStartNextSet,
                onEndGameClicked = onEndGameClicked,
                onDismissRequest = onCloseEndDialog,
            )
        }

        if (uiState.showSummaryDialog) {
            if (uiState.winner != null) {
                SummaryDialog(
                    winner = uiState.winner!!,
                    loser = uiState.loser
                ) {
                    onCloseSummaryDialog()
                    onFinishClicked()
                }
            } else {
                onFinishClicked()
            }
        }

        if (uiState.showPlayersDialog) {
            PlayersDialog(
                orientation = orientation,
                playerOneName = uiState.playerOne.name,
                playerTwoName = uiState.playerTwo.name,
                onSaveClick = { playerOneName, playerTwoName ->
                    onChangePlayersNames(playerOneName, playerTwoName)
                },
                onDismissRequest = onClosePlayersDialog
            )
        }

        if (uiState.showRulesDialog) {
            RulesDialog(
                orientation = orientation,
                players = listOf(
                    uiState.playerOne,
                    uiState.playerTwo
                ),
                maxSets = uiState.maxSets,
                maxScore = uiState.maxScore,
                servingSide = uiState.servingSide,
                onDismissRequest = onCloseRulesDialog,
                onSaveClick = { sets, score, servingSide ->
                    onChangeRules(sets, score, servingSide)
                }
            )
        }

        if (uiState.showRestartModal) {
            RestartGameModal(
                onRestartClicked = onRestartGameClicked,
                onFinishClicked = {
                    onFinishClicked()
                    onCloseRestartModal()
                },
                onDismissRequest = onCloseRestartModal
            )
        }
    }
}