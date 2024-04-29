package presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.app_description
import po11nt.composeapp.generated.resources.new_game_button
import po11nt.composeapp.generated.resources.po11nt_logo
import po11nt.composeapp.generated.resources.privacy_policy
import po11nt.composeapp.generated.resources.version
import presentation.common.PrimaryButton
import utils.PRIVACY_POLICY_URL
import utils.test_tags.StartScreenTags

@Composable
fun StartContent(
    onNewGamePressed: () -> Unit,
    adaptativeRoot: @Composable (@Composable (Modifier) -> Unit) -> Unit
) {
    adaptativeRoot { modifier ->
        AppInfoSide(
            modifier = modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
        )
        NewGameSide(
            modifier = modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
            onNewGamePressed = {
                onNewGamePressed()
            }
        )
    }
}

@Composable
private fun AppInfoSide(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .width(width = 191.dp)
                .height(height = 38.dp),
            painter = painterResource(Res.drawable.po11nt_logo),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .widthIn(max = 191.dp)
                .padding(top = 8.dp),
            text = stringResource(Res.string.app_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun NewGameSide(
    modifier: Modifier = Modifier,
    onNewGamePressed: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = modifier
    ) {
        PrimaryButton(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(StartScreenTags.NewGameButton.tag),
            content = {
                Text(
                    text = stringResource(Res.string.new_game_button),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            onClick = {
                onNewGamePressed()
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.version),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(
                onClick = {
                    uriHandler.openUri(uri = PRIVACY_POLICY_URL)
                }
            ) {
                Text(
                    text = stringResource(Res.string.privacy_policy),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}