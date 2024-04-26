package presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ui.theme.BlueCharcoal
import ui.theme.StormGray

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    textStyle: TextStyle = LocalTextStyle.current,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var focused by remember {
        mutableStateOf(false)
    }
    val labelColor by animateColorAsState(
        if (focused)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = labelColor
    )
    TextField(
        modifier = modifier
            .padding(top = 4.dp)
            .onFocusChanged {
                focused = it.isFocused
            },
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = textStyle,
        readOnly = readOnly,
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = BlueCharcoal
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelLarge,
                color = StormGray
            )
        },
        trailingIcon = trailingIcon
    )
}