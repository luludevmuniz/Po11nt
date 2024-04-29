package presentation.game.dialog

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.exit_button
import po11nt.composeapp.generated.resources.reset_counter
import po11nt.composeapp.generated.resources.restart_the_game
import presentation.common.DefaultDialogContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestartGameModal(
    onDismissRequest: () -> Unit,
    onFinishClicked: () -> Unit,
    onRestartClicked: () -> Unit
) {
    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        onDismissRequest = {
            onDismissRequest()
        }
    )
    {
        DefaultDialogContent(
            modifier = Modifier.navigationBarsPadding(),
            title = stringResource(Res.string.restart_the_game),
            primaryButtonTitle = stringResource(Res.string.reset_counter),
            secondaryButtonTitle = stringResource(Res.string.exit_button),
            onDismissRequest = {
                onDismissRequest()
            },
            onPrimaryButtonClicked = {
                onRestartClicked()
            },
            onSecondaryButtonClicked = {
                onFinishClicked()
            }
        )
    }
}