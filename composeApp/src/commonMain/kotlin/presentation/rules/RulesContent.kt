package presentation.rules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.Player
import domain.model.ServingSide
import domain.model.ServingSide.Left
import domain.model.ServingSide.Right
import presentation.common.CounterField
import presentation.common.PrimaryButton
import presentation.common.TransparentExposedDropdownMenuField
import utils.test_tags.RulesScreenTags

@Composable
fun RulesContent(
    modifier: Modifier = Modifier,
    players: List<Player>,
    maxSets: Int,
    maxScore: Int,
    startServing: ServingSide,
    buttonText: String,
    servingText: String,
    verticalDivider: @Composable () -> Unit = {},
    adaptativeComponent: @Composable (@Composable () -> Unit) -> Unit,
    onMaxSetsCounterChange: (Int) -> Unit,
    onMaxScoreCounterChange: (Int) -> Unit,
    onWhoStartServingChange: (ServingSide) -> Unit,
    onButtonClick: (
        maxSets: Int,
        maxScore: Int,
        startServing: ServingSide
    ) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    adaptativeComponent {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CounterField(
                text = "Sets",
                value = maxSets,
                onValueChange = { sets ->
                    onMaxSetsCounterChange(sets)
                },
                textTextFlag = RulesScreenTags.MaxSetsText.tag,
                increaseTestFlag = RulesScreenTags.IncreaseMaxSetsButton.tag,
                decreaseTestFlag = RulesScreenTags.DecreaseMaxSetsButton.tag
            )
            CounterField(
                text = "Pontos da partida",
                value = maxScore,
                onValueChange = { score ->
                    onMaxScoreCounterChange(score)
                },
                textTextFlag = RulesScreenTags.MaxScoreText.tag,
                increaseTestFlag = RulesScreenTags.IncreaseMaxScoreButton.tag,
                decreaseTestFlag = RulesScreenTags.DecreaseMaxScoreButton.tag
            )
        }
        verticalDivider()
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TransparentExposedDropdownMenuField(
                modifier = Modifier.padding(bottom = 16.dp),
                items = players.map { it.name },
                label = servingText,
                onOptionSelected = { index ->
                    onWhoStartServingChange(if (index == 0) Left else Right)
                }
            )
            PrimaryButton(
                modifier = Modifier.align(Alignment.End),
                content = {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = {
                    expanded.value = !expanded.value
                    onButtonClick(
                        maxSets,
                        maxScore,
                        startServing
                    )
                }
            )
        }
    }
}