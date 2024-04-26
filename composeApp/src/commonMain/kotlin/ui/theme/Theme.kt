package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    primaryContainer = LightGreen,
    onPrimaryContainer = DarkCharcoal,
    surface = DarkCharcoal,
    onSurface = LightGray,
    outline = BlackPearl
)

@Composable
fun Po11ntTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypography(),
        content = content
    )
}
