package presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.close

@Composable
fun DefaultDialogContent(
    modifier: Modifier = Modifier,
    title: String,
    primaryButtonTitle: String,
    secondaryButtonTitle: String,
    onDismissRequest: () -> Unit,
    onPrimaryButtonClicked: () -> Unit,
    onSecondaryButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(Res.string.close),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
            onClick = {
                onSecondaryButtonClicked()
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = secondaryButtonTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
            )
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onPrimaryButtonClicked()
            }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = primaryButtonTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Start
            )
        }
    }
}