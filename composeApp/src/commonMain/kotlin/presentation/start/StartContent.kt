package presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.po11nt_logo
import presentation.common.PrimaryButton
import ui.theme.StormGray
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

@OptIn(ExperimentalResourceApi::class)
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
            text = "Marcador de pontos para partidas de tênis de mesa.",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Light,
            color = StormGray
        )
    }
}

@Composable
fun NewGameSide(
    modifier: Modifier = Modifier,
    onNewGamePressed: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            PrimaryButton(
                modifier = Modifier.testTag(StartScreenTags.NewGameButton.tag),
                content = {
                    Text(
                        text = "NOVA PARTIDA",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = {
                    onNewGamePressed()
                }
            )
        }
        Text(
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 16.dp
            ),
            text = "Versão 1.0.0",
            style = MaterialTheme.typography.labelMedium,
            color = StormGray
        )
        TextButton(
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 16.dp
            ),
            onClick = {
                uriHandler.openUri(uri = "https://github.com/bmaupin/android-pitchpipe/blob/main/metadata/en-US/privacy_policy.txt")
            }
        ) {
            Text(
                text = "Política de Privacidade",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}