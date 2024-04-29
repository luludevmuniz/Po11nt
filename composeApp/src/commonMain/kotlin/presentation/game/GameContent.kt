package presentation.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    switchSides: Boolean,
    onPlayerOneScored: () -> Unit,
    onPlayerTwoScored: () -> Unit,
    onPlayerOneScoreRollback: () -> Unit,
    onPlayerTwoScoreRollback: () -> Unit,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit,
    onSwitchButtonClick: () -> Unit,
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
                switchSides = switchSides,
                shouldRollback = shouldRollback,
                onPlayerOneScored = onPlayerOneScored,
                onPlayerTwoScored = onPlayerTwoScored,
                onShowRestartModal = onShowRestartModal,
                onShowPlayersDialog = onShowPlayersDialog,
                onShowRulesDialog = onShowRulesDialog,
                onSwitchButtonClick = onSwitchButtonClick
            )
        },
        {
            PlayerOneScoreField(
                weight = it,
                actualScore = playerOne.score,
                playerColor = playerOne.color ?: MaterialTheme.colorScheme.primaryContainer,
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
                playerColor = playerTwo.color ?: MaterialTheme.colorScheme.primaryContainer,
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
    switchSides: Boolean,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit,
    onPlayerOneScored: () -> Unit,
    onPlayerTwoScored: () -> Unit,
    onSwitchButtonClick: () -> Unit
) {
    Column {
        GameTopContent(
            leftName = playerOne.name,
            rightName = playerTwo.name,
            leftSetScore = playerOne.sets,
            rightSetScore = playerTwo.sets,
            leftBoxColor = MaterialTheme.colorScheme.secondaryContainer,
            rightBoxColor = MaterialTheme.colorScheme.tertiaryContainer,
            switchSides = switchSides,
            onSwitchButtonClick = onSwitchButtonClick
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
    playerColor: Color,
    onPlayerOneScored: () -> Unit,
    onPlayerOneScoreRollback: () -> Unit,
) {
    Row(modifier = Modifier.weight(weight)) {
        ScoreCounter(
            modifier = Modifier.weight(0.2f),
            actualScore = actualScore,
            maxScore = maxScore,
//            scoreBoxColor = MaterialTheme.colorScheme.secondaryContainer
            scoreBoxColor = playerColor
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
            playerColor = playerColor
        )
    }
}

@Composable
private fun RowScope.PlayerTwoScoreField(
    weight: Float,
    actualScore: Int,
    maxScore: Int,
    playerColor: Color,
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
//            playerColor = MaterialTheme.colorScheme.tertiaryContainer
            playerColor = playerColor
        )
        ScoreCounter(
            modifier = Modifier.weight(0.2f),
            actualScore = actualScore,
            maxScore = maxScore,
            scoreBoxColor = playerColor
        )
    }
}