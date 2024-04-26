package presentation.game.dialog

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.common.DefaultDialogContent
import ui.theme.BlackPearl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestartGameModal(
    onDismissRequest: () -> Unit,
    onFinishClicked: () -> Unit,
    onRestartClicked: () -> Unit
) {
    ModalBottomSheet(
        containerColor = BlackPearl,
        onDismissRequest = {
            onDismissRequest()
        }
    )
    {
        DefaultDialogContent(
            modifier = Modifier.navigationBarsPadding(),
            title = "Quer reiniciar a partida?",
            primaryButtonTitle = "ZERAR CONTADOR",
            secondaryButtonTitle = "SAIR",
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