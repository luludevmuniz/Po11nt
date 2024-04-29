package presentation.players

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.player_one
import po11nt.composeapp.generated.resources.player_one_placeholder
import po11nt.composeapp.generated.resources.player_two
import po11nt.composeapp.generated.resources.player_two_placeholder
import presentation.common.TransparentTextField
import utils.test_tags.PlayersScreenTags

@Composable
fun PlayersContent(
    playerOneName: String = "",
    playerTwoName: String = "",
    onPlayerOneNameChange: (String) -> Unit,
    onPlayerTwoNameChange: (String) -> Unit,
    adaptativeRoot: @Composable (@Composable (Modifier) -> Unit) -> Unit
) {
    adaptativeRoot { modifier ->
        Column(modifier = modifier) {
            TransparentTextField(
                modifier = Modifier
                    .testTag(PlayersScreenTags.PlayerOneTextField.tag)
                    .fillMaxWidth(),
                label = stringResource(Res.string.player_one),
                value = playerOneName,
                onValueChange = { newText ->
                    onPlayerOneNameChange(newText)
                },
                placeholder = stringResource(Res.string.player_one_placeholder),
            )
            Spacer(modifier = Modifier.height(24.dp))
            TransparentTextField(
                modifier = Modifier
                    .testTag(PlayersScreenTags.PlayerTwoTextField.tag)
                    .fillMaxWidth(),
                label = stringResource(Res.string.player_two),
                value = playerTwoName,
                onValueChange = { newText ->
                    onPlayerTwoNameChange(newText)
                },
                placeholder = stringResource(Res.string.player_two_placeholder)
            )
        }
    }
}