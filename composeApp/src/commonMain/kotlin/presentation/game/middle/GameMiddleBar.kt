package presentation.game.middle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.ServingSide
import domain.model.ServingSide.Left
import domain.model.ServingSide.Right
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import po11nt.composeapp.generated.resources.*
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.ic_rollback
import po11nt.composeapp.generated.resources.ic_user_settings
import po11nt.composeapp.generated.resources.ic_whistle
import ui.theme.BlackPearl
import ui.theme.DarkGrayBlue
import ui.theme.StormGray

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun GameMiddleBar(
    servingSide: ServingSide,
    onShowRestartModal: () -> Unit,
    onShowPlayersDialog: () -> Unit,
    onShowRulesDialog: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BlackPearl
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (servingSide == Left) {
            ServingText()
        } else {
            ReceivingText()
        }
        Row(
            modifier = Modifier
                .weight(0.5f)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = DarkGrayBlue)
                    .padding(8.dp)
                    .clickable {
                        onShowRestartModal()
                    },
                painter = painterResource(Res.drawable.ic_rollback),
                contentDescription = "Reiniciar partida",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = DarkGrayBlue)
                    .padding(8.dp)
                    .clickable {
                        onShowRulesDialog()
                    },
                painter = painterResource(Res.drawable.ic_whistle),
                contentDescription = "Regras da partida",
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = DarkGrayBlue)
                    .padding(8.dp)
                    .clickable {
                        onShowPlayersDialog()
                    },
                painter = painterResource(Res.drawable.ic_user_settings),
                contentDescription = "Configurações dos jogadores",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        if (servingSide == Right) {
            ServingText()
        } else {
            ReceivingText()
        }
    }
}

@Composable
private fun RowScope.ReceivingText() {
    Text(
        modifier = Modifier.weight(0.25f),
        text = "Receptor",
        style = MaterialTheme.typography.labelMedium,
        color = StormGray,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun RowScope.ServingText() {
    Row(
        modifier = Modifier.weight(0.25f),
        horizontalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Serviço",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            painter = painterResource(Res.drawable.ic_tenis),
            contentDescription = "Change game rules",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}