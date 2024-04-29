package presentation.game.top

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GameTopContent(
    leftName: String,
    rightName: String,
    leftSetScore: Int,
    rightSetScore: Int,
    leftBoxColor: Color,
    rightBoxColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayersSetScore(
            leftName = leftName,
            rightName = rightName,
            leftSetScore = leftSetScore,
            rightSetRight = rightSetScore,
            leftBoxColor = leftBoxColor,
            rightBoxColor = rightBoxColor
        )
    }
}

@Composable
private fun PlayersSetScore(
    leftName: String,
    rightName: String,
    leftSetScore: Int,
    rightSetRight: Int,
    leftBoxColor: Color,
    rightBoxColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        PlayerSetScore(
            playerName = leftName,
            setScore = leftSetScore,
            backgroundColor = leftBoxColor
        )
        PlayerSetScore(
            playerName = rightName,
            setScore = rightSetRight,
            backgroundColor = rightBoxColor,
            reverseLayout = true,
        )
    }
}

@Composable
private fun RowScope.PlayerSetScore(
    playerName: String,
    setScore: Int,
    backgroundColor: Color,
    reverseLayout: Boolean = false,
) {
    var heightState by remember { mutableStateOf(0.dp) }
    BoxWithConstraints(
        modifier = Modifier.weight(0.5f)
    ) {
        ScoreRow(
            playerName = playerName,
            setScore = setScore,
            height = heightState,
            backgroundColor = backgroundColor,
            onHeightStateChange = {
                heightState = it
            },
            reverseLayout = reverseLayout
        )
    }
}

@Composable
private fun ScoreRow(
    playerName: String,
    setScore: Int,
    height: Dp,
    backgroundColor: Color,
    onHeightStateChange: (Dp) -> Unit,
    reverseLayout: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (nameBox, scoreBox) = createSetBoxes(
            playerName,
            setScore,
            height,
            backgroundColor,
            onHeightStateChange
        )
        if (reverseLayout) {
            scoreBox()
            nameBox()
        } else {
            nameBox()
            scoreBox()
        }
    }
}

private fun RowScope.createSetBoxes(
    playerName: String,
    setScore: Int,
    height: Dp,
    backgroundColor: Color,
    onHeightStateChange: (Dp) -> Unit
): Pair<@Composable () -> Unit, @Composable () -> Unit> {
    val nameBox: @Composable () -> Unit = {
        PlayerNameBox(
            playerName = playerName,
            height = height,
            backgroundColor = backgroundColor
        )
    }

    val scoreBox: @Composable () -> Unit = {
        SetBox(
            setScore = setScore,
            onHeightStateChange = onHeightStateChange
        )
    }

    return Pair(nameBox, scoreBox)
}

@Composable
private fun RowScope.PlayerNameBox(
    modifier: Modifier = Modifier,
    playerName: String,
    height: Dp,
    backgroundColor: Color
) {
    Box(
        modifier = modifier
            .weight(0.59f)
            .height(height)
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = playerName,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun RowScope.SetBox(
    modifier: Modifier = Modifier,
    setScore: Int,
    onHeightStateChange: (Dp) -> Unit
) {
    Layout(
        modifier = modifier
            .weight(0.49f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
            .wrapContentWidth(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = setScore,
                    transitionSpec = {
                        expandVertically() togetherWith shrinkVertically()
                    }
                ) {
                    Text(
                        text = "$it",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        measurePolicy = { measurable, _ ->
            val placeable = measurable.first().measure(Constraints())
            onHeightStateChange(placeable.height.toDp())
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }
    )
}