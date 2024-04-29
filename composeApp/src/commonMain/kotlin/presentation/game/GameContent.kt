package presentation.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import domain.model.Player
import domain.model.ServingSide
import presentation.game.middle.GameMiddleContent
import presentation.game.side.ScoreCounter
import presentation.game.top.GameTopContent

@Composable
fun GameContent(
    playerOne: Player,
    playerTwo: Player,
    maxScore: Int,
    servingSide: ServingSide,
    shouldRollback: MutableState<Pair<Boolean, Boolean>>,
    onPlayerOneScored: () -> Unit,
    onPlayerTwoScored: () -> Unit,
    onPlayerOneScoreRollback: () -> Unit,
    onPlayerTwoScoreRollback: () -> Unit,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit,
    adaptativeContent: @Composable (
        centerContent: @Composable (Modifier) -> Unit,
        playerOneScoreField: @Composable RowScope.(Float) -> Unit,
        playerTwoScoreField: @Composable RowScope.(Float) -> Unit
    ) -> Unit
) {
    adaptativeContent(
        {
            CenterContent(
                middleContentModifier = it,
                playerOne = playerOne,
                playerTwo = playerTwo,
                servingSide = servingSide,
                shouldRollback = shouldRollback,
                onPlayerOneScored = onPlayerOneScored,
                onPlayerTwoScored = onPlayerTwoScored,
                onShowRestartModal = {
                    onShowRestartModal()
                },
                onShowPlayersDialog = {
                    onShowPlayersDialog()
                },
                onShowRulesDialog = {
                    onShowRulesDialog()
                }
            )
        },
        {
            PlayerOneScoreField(
                weight = it,
                actualScore = playerOne.score,
                maxScore = maxScore,
                onPlayerOneScored = {
                    onPlayerOneScored()
                },
                onPlayerOneScoreRollback = {
                    onPlayerOneScoreRollback()
                }
            )
        },
        {
            PlayerTwoScoreField(
                weight = it,
                actualScore = playerTwo.score,
                maxScore = maxScore,
                onPlayerTwoScored = {
                    onPlayerTwoScored()
                },
                onPlayerTwoScoreRollback = {
                    onPlayerTwoScoreRollback()
                }
            )
        }
    )
}

@Composable
private fun CenterContent(
    middleContentModifier: Modifier,
    playerOne: Player,
    playerTwo: Player,
    servingSide: ServingSide,
    shouldRollback: MutableState<Pair<Boolean, Boolean>>,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit,
    onPlayerOneScored: () -> Unit,
    onPlayerTwoScored: () -> Unit
) {
    Column {
        GameTopContent(
            leftName = playerOne.name,
            rightName = playerTwo.name,
            leftSetScore = playerOne.sets,
            rightSetScore = playerTwo.sets,
            leftBoxColor = MaterialTheme.colorScheme.secondaryContainer,
            rightBoxColor = MaterialTheme.colorScheme.tertiaryContainer
        )

        GameMiddleContent(
            modifier = middleContentModifier,
            sideServing = servingSide,
            currentPlayerOneScore = playerOne.score,
            currentPlayerTwoScore = playerTwo.score,
            shouldRollback = shouldRollback.value,
            onRollBack = {
                shouldRollback.value = it
            },
            onShowRestartModal = {
                onShowRestartModal()
            },
            onShowPlayersDialog = {
                onShowPlayersDialog()
            },
            onShowRulesDialog = {
                onShowRulesDialog()
            },
            onPlayerOneCurrentScoreChanged = {
                onPlayerOneScored()
            },
            onPlayerTwoScoreCurrentChanged = {
                onPlayerTwoScored()
            }
        )
    }
}

@Composable
private fun RowScope.PlayerOneScoreField(
    weight: Float,
    actualScore: Int,
    maxScore: Int,
    onPlayerOneScored: () -> Unit,
    onPlayerOneScoreRollback: () -> Unit,
) {
    Row(modifier = Modifier.weight(weight)) {
        ScoreCounter(
            modifier = Modifier.weight(0.2f),
            actualScore = actualScore,
            maxScore = maxScore,
            scoreBoxColor = MaterialTheme.colorScheme.secondaryContainer
        )
        SwipeToIncreaseScore(
            modifier = Modifier.weight(0.8f),
            onScoreIncrease = {
                onPlayerOneScored()
            },
            onScoreDecreased = {
                if (actualScore > 0) {
                    onPlayerOneScoreRollback()
                }
            },
            playerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}

@Composable
private fun RowScope.PlayerTwoScoreField(
    weight: Float,
    actualScore: Int,
    maxScore: Int,
    onPlayerTwoScored: () -> Unit,
    onPlayerTwoScoreRollback: () -> Unit,
) {
    Row(modifier = Modifier.weight(weight)) {
        SwipeToIncreaseScore(
            modifier = Modifier.weight(0.8f),
            onScoreIncrease = {
                onPlayerTwoScored()
            },
            onScoreDecreased = {
                if (actualScore > 0) {
                    onPlayerTwoScoreRollback()
                }
            },
            playerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
        ScoreCounter(
            modifier = Modifier.weight(0.2f),
            actualScore = actualScore,
            maxScore = maxScore,
            scoreBoxColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}