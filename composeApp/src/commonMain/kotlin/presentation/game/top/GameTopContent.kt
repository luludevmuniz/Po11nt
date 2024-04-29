package presentation.game.top

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.ic_switch_arrows_24dp

@Composable
fun GameTopContent(
    leftName: String,
    rightName: String,
    leftSetScore: Int,
    rightSetScore: Int,
    leftBoxColor: Color,
    rightBoxColor: Color,
    switchSides: Boolean,
    onSwitchButtonClick: () -> Unit
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
            rightBoxColor = rightBoxColor,
            switchSides = switchSides,
            onSwitchButtonClick = onSwitchButtonClick
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
    rightBoxColor: Color,
    switchSides: Boolean,
    onSwitchButtonClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            PlayerSetScore(
                modifier = Modifier.weight(0.5f),
                playerName = leftName,
                switchSides = switchSides,
                setScore = leftSetScore,
                backgroundColor = leftBoxColor
            )
            PlayerSetScore(
                modifier = Modifier.weight(0.5f),
                playerName = rightName,
                switchSides = switchSides,
                setScore = rightSetRight,
                backgroundColor = rightBoxColor,
                reverseLayout = true,
            )
        }
        IconButton(onClick = onSwitchButtonClick) {
            Icon(
                painter = painterResource(Res.drawable.ic_switch_arrows_24dp),
                contentDescription = "Switch sides"
            )
        }
    }
}

@Composable
private fun PlayerSetScore(
    modifier: Modifier,
    playerName: String,
    switchSides: Boolean,
    setScore: Int,
    backgroundColor: Color,
    reverseLayout: Boolean = false,
) {
    var heightState by remember { mutableStateOf(0.dp) }
    BoxWithConstraints(modifier = modifier) {
        ScoreRow(
            playerName = playerName,
            setScore = setScore,
            switchSides = switchSides,
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
    switchSides: Boolean,
    height: Dp,
    backgroundColor: Color,
    onHeightStateChange: (Dp) -> Unit,
    reverseLayout: Boolean
) {
    var maxWidth by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                maxWidth = it.size.width
            }
    ) {
        val (nameBox, scoreBox) = createSetBoxes(
            playerName = playerName,
            setScore = setScore,
            switchSides = switchSides,
            reverseLayout = reverseLayout,
            height = height,
            backgroundColor = backgroundColor,
            onHeightStateChange = onHeightStateChange,
            maxScreenWidth = maxWidth
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
    modifier: Modifier = Modifier,
    playerName: String,
    reverseLayout: Boolean,
    switchSides: Boolean,
    setScore: Int,
    height: Dp,
    maxScreenWidth: Int,
    backgroundColor: Color,
    onHeightStateChange: (Dp) -> Unit
): Pair<@Composable () -> Unit, @Composable () -> Unit> {
    val nameBox: @Composable () -> Unit = {
        PlayerNameBox(
            modifier = modifier,
            playerName = playerName,
            height = height,
            switchSides = switchSides,
            reverseLayout = reverseLayout,
            maxScreenWidth = maxScreenWidth,
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
    reverseLayout: Boolean,
    switchSides: Boolean,
    height: Dp,
    maxScreenWidth: Int,
    backgroundColor: Color
) {
    var componentWidth by remember {
        mutableIntStateOf(0)
    }
    val offset by animateIntOffsetAsState(
        targetValue = if (switchSides) {
            IntOffset(maxScreenWidth.times(2) - componentWidth, 0)
        } else {
            IntOffset.Zero
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "offset"
    )
    Box(
        modifier = modifier
            .offset {
                if (reverseLayout) -offset else offset
            }
            .onGloballyPositioned {
                componentWidth = it.size.width
            }
            .weight(0.59f)
            .height(height)
            .background(color = backgroundColor.copy(alpha = 0.3f))
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