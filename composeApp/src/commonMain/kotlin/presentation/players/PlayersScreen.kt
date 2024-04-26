package presentation.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import presentation.common.DefaultTopAppBar
import presentation.common.PrimaryButton
import presentation.players.PlayersEvent.OnPlayerOneNameChanged
import presentation.players.PlayersEvent.OnPlayerTwoNameChanged
import ui.theme.StormGray
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical
import utils.test_tags.PlayersScreenTags

@Composable
fun PlayersScreen(
    onNavigationIconClick: () -> Unit,
    onNextButtonClick: (String, String) -> Unit,
    orientation: Orientation
) {
    val viewModel = viewModel {
        PlayersViewModel()
    }
    val buttonText = "PRÓXIMO"
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "Jogadores da partida",
                onNavigationIconClick = {
                    onNavigationIconClick()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        PlayersContent(
            playerOneName = uiState.playerOneName,
            playerTwoName = uiState.playerTwoName,
            onPlayerOneNameChange = { newText ->
                viewModel.onEvent(OnPlayerOneNameChanged(newText))
            },
            onPlayerTwoNameChange = { newText ->
                viewModel.onEvent(OnPlayerTwoNameChanged(newText))
            }
        ) { content ->
            when (orientation) {
                Vertical -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = paddingValues)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            ),
                        verticalArrangement = Arrangement.Top
                    ) {
                        content(Modifier)
                        PrimaryButton(
                            modifier = Modifier
                                .testTag(PlayersScreenTags.NextButton.tag)
                                .padding(top = 24.dp)
                                .align(Alignment.End),
                            content = {
                                Text(
                                    text = buttonText,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            },
                            onClick = {
                                onNextButtonClick(
                                    uiState.playerOneName,
                                    uiState.playerTwoName
                                )
                            }
                        )
                    }
                }

                Horizontal -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = paddingValues)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            ),
                    ) {
                        content(Modifier.weight(0.6f))
                        Row(
                            modifier = Modifier.weight(0.4f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = 8.dp)
                                    .width(1.dp)
                                    .background(color = StormGray)
                            )
                            PrimaryButton(
                                modifier = Modifier.testTag(PlayersScreenTags.NextButton.tag),
                                content = {
                                    Text(
                                        text = buttonText,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                },
                                onClick = {
                                    onNextButtonClick(
                                        uiState.playerOneName,
                                        uiState.playerTwoName
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}