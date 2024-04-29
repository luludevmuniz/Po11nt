package presentation.rules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Player
import domain.model.ServingSide
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.rules_top_app_bar_title
import po11nt.composeapp.generated.resources.start_button
import po11nt.composeapp.generated.resources.start_serving
import presentation.common.DefaultTopAppBar
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical

@Composable
fun RulesScreen(
    players: List<Player>,
    uiState: RulesState,
    onMaxSetsCounterChange: (Int) -> Unit,
    onMaxScoreCounterChange: (Int) -> Unit,
    onWhoStartServingChange: (ServingSide) -> Unit,
    orientation: Orientation,
    onNavigationIconClick: () -> Unit,
    onNextButtonClick: (
        maxSets: Int,
        maxScore: Int,
        startServing: ServingSide
    ) -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(Res.string.rules_top_app_bar_title),
                onNavigationIconClick = {
                    onNavigationIconClick()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        RulesContent(
            modifier = Modifier.fillMaxWidth(if (orientation == Horizontal) 0.5f else 1f),
            players = players,
            maxSets = uiState.maxSets,
            maxScore = uiState.maxScore,
            startServing = uiState.startServing,
            buttonText = stringResource(Res.string.start_button),
            servingText = stringResource(Res.string.start_serving),
            onButtonClick = { maxSets, maxScore, startServing ->
                onNextButtonClick(
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
                            .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    )
                }
            },
            onMaxSetsCounterChange = { sets ->
                onMaxSetsCounterChange(sets)
            },
            onMaxScoreCounterChange = { score ->
                onMaxScoreCounterChange(score)
            },
            onWhoStartServingChange = { servingSide ->
                onWhoStartServingChange(servingSide)
            },
            adaptativeComponent = { content ->
                when (orientation) {
                    Horizontal -> {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(it)
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
                                .padding(it)
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