package presentation.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.decrease_count
import po11nt.composeapp.generated.resources.ic_add_green_24dp
import po11nt.composeapp.generated.resources.ic_remove_24dp
import po11nt.composeapp.generated.resources.increase_count

@Composable
fun CounterField(
    modifier: Modifier = Modifier,
    text: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    textTextFlag: String,
    increaseTestFlag: String,
    decreaseTestFlag: String,
    ) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Counter(
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                textTextFlag = textTextFlag,
                increaseTestFlag = increaseTestFlag,
                decreaseTestFlag = decreaseTestFlag
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    }
}

@Composable
private fun Counter(
    value: Int,
    onValueChange: (Int) -> Unit,
    textTextFlag: String,
    increaseTestFlag: String,
    decreaseTestFlag: String,
    ) {
    var adding by remember { mutableStateOf(false) }
    val decreaseIconColor = animateColorAsState(
        if (value == 1) MaterialTheme.colorScheme.surfaceContainerHighest
        else MaterialTheme.colorScheme.primary
    )
    Row(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceContainer),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.ic_remove_24dp),
            modifier = Modifier
                .testTag(decreaseTestFlag)
                .clickable {
                    if (value != 1) {
                        adding = false
                        onValueChange(value.minus(1))
                    }
                }
                .padding(4.dp),
            contentDescription = stringResource(Res.string.decrease_count),
            tint = decreaseIconColor.value
        )
        AnimatedContent(
            targetState = value,
            transitionSpec = {
                slideInVertically {
                    if (adding)
                        it
                    else
                        -it
                } togetherWith slideOutVertically {
                    if (adding)
                        -it
                    else
                        it
                }
            }
        ) {
            Text(
                modifier = Modifier.testTag(textTextFlag),
                text = it.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Icon(
            painterResource(Res.drawable.ic_add_green_24dp),
            modifier = Modifier
                .testTag(increaseTestFlag)
                .clickable {
                    adding = true
                    onValueChange(value.plus(1))
                }
                .padding(4.dp),
            contentDescription = stringResource(Res.string.increase_count),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}