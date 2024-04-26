package presentation.rules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Player
import domain.model.ServingSide
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import presentation.common.DefaultTopAppBar
import presentation.rules.RulesEvent.*
import ui.theme.StormGray
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical

@Composable
fun RulesScreen(
    players: List<Player>,
    orientation: Orientation,
    onNavigationIconClick: () -> Unit,
    onNextButtonClick: (
        maxSets: Int,
        maxScore: Int,
        startServing: ServingSide
    ) -> Unit
) {
    val viewModel = viewModel(
        modelClass = RulesViewModel::class,
        keys = listOf(2)
    ) {
        RulesViewModel()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Regras da disputa",
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
            maxSets = uiState.value.maxSets,
            maxScore = uiState.value.maxScore,
            startServing = uiState.value.startServing,
            buttonText = "INICIAR",
            servingText = "Inicia sacando",
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
                            .background(color = StormGray)
                    )
                }
            },
            onMaxSetsCounterChange = { sets ->
                viewModel.onEvent(OnMaxSetsCounterChange(sets))
            },
            onMaxScoreCounterChange = { score ->
                viewModel.onEvent(OnMaxScoreCounterChange(score))
            },
            onWhoStartServingChange = { servingSide ->
                viewModel.onEvent(OnWhoStartServingChange(servingSide))
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