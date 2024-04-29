package presentation.game.dialog

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.continue_button
import po11nt.composeapp.generated.resources.end_match
import presentation.common.DefaultDialogContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetEndDialog(
    title: String,
    onContinueClicked: () -> Unit,
    onEndGameClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = {}) {
        DefaultDialogContent(
            title = title,
            primaryButtonTitle = stringResource(Res.string.continue_button),
            secondaryButtonTitle = stringResource(Res.string.end_match),
            onPrimaryButtonClicked = {
                onContinueClicked()
            },
            onSecondaryButtonClicked = {
                onEndGameClicked()
            },
            onDismissRequest = {
                onDismissRequest()
            }
        )
    }
}