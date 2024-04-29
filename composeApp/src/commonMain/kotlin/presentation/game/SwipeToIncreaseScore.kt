package presentation.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.ic_back_17dp
import po11nt.composeapp.generated.resources.ic_double_arrow_up_24dp
import po11nt.composeapp.generated.resources.ic_plus_one_24dp
import po11nt.composeapp.generated.resources.rewind_score
import po11nt.composeapp.generated.resources.swipe_to_increase_score
import kotlin.math.absoluteValue

@Composable
fun SwipeToIncreaseScore(
    modifier: Modifier = Modifier,
    onScoreIncrease: () -> Unit,
    onScoreDecreased: () -> Unit,
    playerColor: Color
) {
    val dragLimitVerticalPx = remember { mutableStateOf(0f) }
    Column(
        modifier = modifier
            .fillMaxHeight()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
            .onGloballyPositioned {
                dragLimitVerticalPx.value = it.size.height.toFloat() * 0.25f
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DecreaseScoreButton(onScoreDecreased = { onScoreDecreased() })
        val thumbOffsetY = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()
        val reachedComponent = remember { mutableStateOf(false) }
        val alpha = animateFloatAsState(if (reachedComponent.value) 0f else 1f)
        val backgroundColor = animateColorAsState(
            targetValue = if (reachedComponent.value) playerColor
            else Color.Transparent
        )
        val borderColor = animateColorAsState(
            targetValue = if (reachedComponent.value) playerColor
            else MaterialTheme.colorScheme.outlineVariant
        )
        val iconColor = animateColorAsState(
            targetValue = if (reachedComponent.value) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.outlineVariant
        )
        Surface(
            shape = CircleShape,
            border = BorderStroke(
                width = 2.dp,
                color = borderColor.value
            ),
            color = backgroundColor.value
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_plus_one_24dp),
                    contentDescription = stringResource(Res.string.swipe_to_increase_score),
                    tint = iconColor.value
                )
            }
        }
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = thumbOffsetY.value.toInt()
                    )
                }
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            event.changes.forEach { pointerInputChange ->
                                scope.launch {
                                    val targetValue =
                                        thumbOffsetY.value + pointerInputChange.positionChange().y
                                    thumbOffsetY.snapTo(targetValue)
                                    // detect drag to limit
                                    if (thumbOffsetY.value.absoluteValue >= dragLimitVerticalPx.value) {
                                        reachedComponent.value = true
                                    }
                                    if (thumbOffsetY.value != 0f) {
                                        thumbOffsetY.animateTo(
                                            targetValue = 0f,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = StiffnessLow
                                            )
                                        )
                                        reachedComponent.value = false
                                    }
                                }
                            }
                        } while (event.changes.any { it.pressed })
                        if (reachedComponent.value) {
                            onScoreIncrease()
                        }
                    }
                }
                .alpha(alpha.value)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onSurface)
                .padding(12.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.ic_double_arrow_up_24dp),
                contentDescription = stringResource(Res.string.swipe_to_increase_score),
                tint = MaterialTheme.colorScheme.surfaceContainer
            )
        }
    }
}

@Composable
private fun DecreaseScoreButton(onScoreDecreased: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onScoreDecreased()
            }
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_back_17dp),
            contentDescription = stringResource(Res.string.rewind_score),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}