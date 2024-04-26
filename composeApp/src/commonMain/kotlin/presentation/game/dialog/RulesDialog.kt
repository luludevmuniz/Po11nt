package presentation.game.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Player
import domain.model.ServingSide
import presentation.common.FullScreenDialog
import presentation.rules.RulesContent
import ui.theme.StormGray
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical

@Composable
fun RulesDialog(
    orientation: Orientation,
    players: List<Player>,
    maxSets: Int,
    maxScore: Int,
    servingSide: ServingSide,
    onSaveClick: (
        maxSets: Int,
        maxScore: Int,
        servingSide: ServingSide
    ) -> Unit,
    onDismissRequest: () -> Unit
) {
    var updatedMaxSets by remember {
        mutableStateOf(maxSets)
    }
    var updatedMaxScore by remember {
        mutableStateOf(maxScore)
    }
    var updatedServingSide by remember {
        mutableStateOf(servingSide)
    }
    FullScreenDialog(
        title = "Regras da disputa",
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        RulesContent(
            modifier = Modifier.fillMaxWidth(if (orientation == Horizontal) 0.5f else 1f),
            players = players,
            maxSets = updatedMaxSets,
            maxScore = updatedMaxScore,
            startServing = updatedServingSide,
            buttonText = "SALVAR",
            servingText = "Sacando agora",
            onButtonClick = { maxSets, maxScore, startServing ->
                onSaveClick(
                    maxSets,
                    maxScore,
                    startServing
                )
            },
            verticalDivider = {
                if (orientation == Horizontal) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                            .width(1.dp)
                            .background(color = StormGray)
                    )
                }
            },
            onMaxSetsCounterChange = { sets ->
                updatedMaxSets = sets
            },
            onMaxScoreCounterChange = { score ->
                updatedMaxScore = score
            },
            onWhoStartServingChange = { player ->
                updatedServingSide = player
            },
            adaptativeComponent = { content ->
                when (orientation) {
                    Horizontal -> {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    vertical = 24.dp,
                                    horizontal = 16.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            content()
                        }
                    }

                    Vertical -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    vertical = 24.dp,
                                    horizontal = 16.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            content()
                        }
                    }
                }
            }
        )
    }
}