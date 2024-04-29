package presentation.game.middle

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.ServingSide
import domain.model.ServingSide.Left
import domain.model.ServingSide.Right
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.currently_serving
import po11nt.composeapp.generated.resources.game_rules
import po11nt.composeapp.generated.resources.ic_rollback
import po11nt.composeapp.generated.resources.ic_tenis
import po11nt.composeapp.generated.resources.ic_user_settings
import po11nt.composeapp.generated.resources.ic_whistle
import po11nt.composeapp.generated.resources.players_settings
import po11nt.composeapp.generated.resources.receiver
import po11nt.composeapp.generated.resources.restart_game
import po11nt.composeapp.generated.resources.service

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
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            modifier = Modifier.weight(0.25f),
            targetState = servingSide,
            transitionSpec = {
                slideInHorizontally(animationSpec = tween(200)) {
                    if (this.targetState == Left)
                        -it
                    else
                        it
                } togetherWith slideOutHorizontally(animationSpec = tween(200)) {
                    if (this.targetState == Left)
                        it
                    else
                        -it
                }
            }
        ) {
            LaunchedEffect(Unit) {
                delay(200)
            }
            if (servingSide == Left) {
                ServingText()
            } else {
                ReceivingText()
            }
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
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp)
                    .clickable {
                        onShowRestartModal()
                    },
                painter = painterResource(Res.drawable.ic_rollback),
                contentDescription = stringResource(Res.string.restart_game),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp)
                    .clickable {
                        onShowRulesDialog()
                    },
                painter = painterResource(Res.drawable.ic_whistle),
                contentDescription = stringResource(Res.string.game_rules),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp)
                    .clickable {
                        onShowPlayersDialog()
                    },
                painter = painterResource(Res.drawable.ic_user_settings),
                contentDescription = stringResource(Res.string.players_settings),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        AnimatedContent(
            modifier = Modifier.weight(0.25f),
            targetState = servingSide,
            transitionSpec = {
                slideInHorizontally(animationSpec = tween(200)) {
                    if (this.targetState == Right)
                        it
                    else
                        -it
                } togetherWith slideOutHorizontally(animationSpec = tween(200)) {
                    if (this.targetState == Right)
                        -it
                    else
                        it
                }
            }
        ) {
            LaunchedEffect(Unit) {
                delay(200)
            }
            if (it == Right) {
                ServingText()
            } else {
                ReceivingText()
            }
        }
    }
}

@Composable
private fun ReceivingText() {
    Text(
        text = stringResource(Res.string.receiver),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ServingText() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.service),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            painter = painterResource(Res.drawable.ic_tenis),
            contentDescription = stringResource(Res.string.currently_serving),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}