package presentation.game.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
            primaryButtonTitle = "CONTINUAR",
            secondaryButtonTitle = "ENCERRAR PARTIDA",
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