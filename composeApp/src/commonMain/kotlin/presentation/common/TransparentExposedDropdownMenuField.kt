package presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.open_menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentExposedDropdownMenuField(
    modifier: Modifier = Modifier,
    items: List<String>,
    label: String,
    onOptionSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
    ) {
        TransparentTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .menuAnchor(),
            value = selectedOptionText,
            onValueChange = {},
            readOnly = true,
            label = label,
            textStyle = MaterialTheme.typography.bodySmall,
            trailingIcon = {
                Icon(
                    modifier = Modifier.rotate(rotation),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(Res.string.open_menu)
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            items.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(selectionOption)
                    },
                    onClick = {
                        selectedOptionText = selectionOption
                        onOptionSelected(index)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}