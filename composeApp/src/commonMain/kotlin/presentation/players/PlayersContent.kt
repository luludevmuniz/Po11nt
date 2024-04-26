package presentation.players

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
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
                label = "Player 1",
                value = playerOneName,
                onValueChange = { newText ->
                    onPlayerOneNameChange(newText)
                },
                placeholder = "Nome do Player 1",
            )
            Spacer(modifier = Modifier.height(24.dp))
            TransparentTextField(
                modifier = Modifier
                    .testTag(PlayersScreenTags.PlayerTwoTextField.tag)
                    .fillMaxWidth(),
                label = "Player 2",
                value = playerTwoName,
                onValueChange = { newText ->
                    onPlayerTwoNameChange(newText)
                },
                placeholder = "Nome do Player 2"
            )
        }
    }
}