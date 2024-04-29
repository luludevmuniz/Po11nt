package presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.go_back
import po11nt.composeapp.generated.resources.ic_return_white_32dp

@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 48.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.Start
        )
    ) {
        IconButton(
            onClick = {
                onNavigationIconClick()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_return_white_32dp),
                contentDescription = stringResource(Res.string.go_back)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }
}