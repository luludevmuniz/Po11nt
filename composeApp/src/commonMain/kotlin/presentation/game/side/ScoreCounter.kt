package presentation.game.side

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.DarkGrayBlue
import ui.theme.StormGray

@Composable
fun ScoreCounter(
    modifier: Modifier = Modifier,
    actualScore: Int,
    maxScore: Int,
    scoreBoxColor: Color
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(0.9f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            (1..maxScore).reversed().forEach {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(10.dp)
                        .weight(1f)
                        .background(
                            color = animateColorAsState(
                                targetValue = if (
                                    actualScore >= it) scoreBoxColor
                                else DarkGrayBlue,
                                animationSpec = tween(
                                    500,
                                    easing = LinearOutSlowInEasing
                                )
                            ).value
                        )
                )
            }
        }
        Row(
            modifier = Modifier.weight(0.1f),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = actualScore.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = StormGray
            )
            Text(
                text = "/$maxScore",
                style = MaterialTheme.typography.labelSmall,
                color = StormGray
            )
        }
    }
}