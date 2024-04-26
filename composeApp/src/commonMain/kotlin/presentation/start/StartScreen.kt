package presentation.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.Orientation
import utils.Orientation.Horizontal
import utils.Orientation.Vertical

@Composable
fun StartScreen(
    orientation: Orientation,
    onNewGamePressed: () -> Unit
) {
    Scaffold { paddingValues ->
        StartContent(
            onNewGamePressed = {
                onNewGamePressed()
            }
        ) { content ->
            when (orientation) {
                Vertical -> {
                    Column(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(paddingValues = paddingValues)
                            .padding(24.dp)
                    ) {
                        content(Modifier.weight(0.5f))
                    }
                }

                Horizontal -> {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(paddingValues = paddingValues)
                            .padding(24.dp)
                    ) {
                        content(Modifier.weight(0.5f))
                    }
                }
            }
        }
    }
}