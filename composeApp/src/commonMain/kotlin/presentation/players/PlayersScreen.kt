package presentation.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.next_button
import po11nt.composeapp.generated.resources.players_top_app_bar_title
import presentation.common.DefaultTopAppBar
import presentation.common.PrimaryButton
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical
import utils.test_tags.PlayersScreenTags

@Composable
fun PlayersScreen(
    onNavigationIconClick: () -> Unit,
    onNextButtonClick: (String, String) -> Unit,
    uiState: PlayersState,
    onPlayerOneNameChanged: (String) -> Unit,
    onPlayerTwoNameChanged: (String) -> Unit,
    orientation: Orientation
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(Res.string.players_top_app_bar_title),
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
            onPlayerOneNameChange = { name ->
                onPlayerOneNameChanged(name)
            },
            onPlayerTwoNameChange = { name ->
                onPlayerTwoNameChanged(name)
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
                                    text = stringResource(Res.string.next_button),
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
                                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                            )
                            PrimaryButton(
                                modifier = Modifier.testTag(PlayersScreenTags.NextButton.tag),
                                content = {
                                    Text(
                                        text = stringResource(Res.string.next_button),
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