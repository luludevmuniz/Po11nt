package presentation.game.middle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.model.ServingSide

@Composable
fun GameMiddleContent(
    modifier: Modifier = Modifier,
    sideServing: ServingSide,
    currentPlayerOneScore: Int,
    currentPlayerTwoScore: Int,
    onPlayerOneCurrentScoreChanged: () -> Unit,
    onPlayerTwoScoreCurrentChanged: () -> Unit,
    shouldRollback: Pair<Boolean, Boolean>,
    onRollBack: (Pair<Boolean, Boolean>) -> Unit,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        Scoreboard(
            modifier = Modifier.weight(0.8f),
            currentPlayerOneScore = currentPlayerOneScore,
            currentPlayerTwoScore = currentPlayerTwoScore,
            onCurrentPlayerOneScoreChanged = { onPlayerOneCurrentScoreChanged() },
            onCurrentPlayerTwoScoreChanged = { onPlayerTwoScoreCurrentChanged() },
            shouldRollback = shouldRollback,
            onRollBack = {
                onRollBack(it)
            }
        )
        GameMiddleBar(
            servingSide = sideServing,
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
    }
}