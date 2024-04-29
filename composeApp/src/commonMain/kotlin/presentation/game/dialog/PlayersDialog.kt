package presentation.game.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.player_one
import po11nt.composeapp.generated.resources.player_two
import po11nt.composeapp.generated.resources.players_top_app_bar_title
import po11nt.composeapp.generated.resources.save_button
import presentation.common.FullScreenDialog
import presentation.common.PrimaryButton
import presentation.players.PlayersContent
import utils.Orientation

@Composable
fun PlayersDialog(
    orientation: Orientation,
    playerOneName: String,
    playerTwoName: String,
    onSaveClick: (String, String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var updatedPlayerOneName by remember {
        mutableStateOf(playerOneName)
    }
    var updatedPlayerTwoName by remember {
        mutableStateOf(playerTwoName)
    }
    val playerOneDefaultName = stringResource(Res.string.player_one)
    val playerTwoDefaultName = stringResource(Res.string.player_two)
    FullScreenDialog(
        title = stringResource(Res.string.players_top_app_bar_title),
        onDismissRequest = { onDismissRequest() }
    ) {
        PlayersContent(
            playerOneName = updatedPlayerOneName,
            playerTwoName = updatedPlayerTwoName,
            onPlayerOneNameChange = { newText ->
                updatedPlayerOneName = newText
            },
            onPlayerTwoNameChange = { newText ->
                updatedPlayerTwoName = newText
            },
        ) { content ->
            when (orientation) {
                Orientation.Vertical -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            ),
                        verticalArrangement = Arrangement.Top
                    ) {
                        content(Modifier)
                        PrimaryButton(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .align(Alignment.End),
                            content = {
                                Text(
                                    text = stringResource(Res.string.save_button),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            },
                            onClick = {
                                onSaveClick(
                                    updatedPlayerOneName.ifBlank { playerOneDefaultName },
                                    updatedPlayerTwoName.ifBlank { playerTwoDefaultName }
                                )
                            }
                        )
                    }
                }

                Orientation.Horizontal -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
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
                                content = {
                                    Text(
                                        text = stringResource(Res.string.save_button),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                },
                                onClick = {
                                    onSaveClick(
                                        updatedPlayerOneName.ifBlank { playerOneDefaultName },
                                        updatedPlayerTwoName.ifBlank { playerTwoDefaultName }
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