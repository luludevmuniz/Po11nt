package rules

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import domain.model.Player
import presentation.rules.RulesEvent
import presentation.rules.RulesScreen
import presentation.rules.RulesViewModel
import utils.Orientation
import utils.test_tags.RulesScreenTags
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class RulesScreenUiTest {
    private lateinit var viewModel: RulesViewModel
    private val defaultPlayers = listOf(
        Player(name = "Player 1"),
        Player(name = "Player 2")
    )
    private val defaultMaxSets = 5
    private val defaultMaxScore = 11

    private fun ComposeUiTest.setUpRulesScreen() {
        setContent {
            RulesScreen(
                players = defaultPlayers,
                uiState = viewModel.uiState.collectAsState().value,
                onMaxSetsCounterChange = { sets ->
                    viewModel.onEvent(
                        RulesEvent.OnMaxSetsCounterChange(
                            sets
                        )
                    )
                },
                onMaxScoreCounterChange = { score ->
                    viewModel.onEvent(
                        RulesEvent.OnMaxScoreCounterChange(
                            score
                        )
                    )
                },
                onWhoStartServingChange = { servingSide ->
                    viewModel.onEvent(
                        RulesEvent.OnWhoStartServingChange(
                            servingSide
                        )
                    )
                },
                orientation = Orientation.Vertical,
                onNavigationIconClick = {},
                onNextButtonClick = {_, _, _ -> }
            )
        }
    }

    @BeforeTest
    fun setUpViewModel() {
        viewModel = RulesViewModel()
    }

    @Test
    fun defaultMaxSetsShouldBeFive() = runComposeUiTest {
        setUpRulesScreen()
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.toString())
    }

    @Test
    fun shouldIncreaseMaxSet() = runComposeUiTest {
        setUpRulesScreen()
        val increaseSetsButton = onNodeWithTag(RulesScreenTags.IncreaseMaxSetsButton.tag)
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        increaseSetsButton.performClick()
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.plus(1).toString())
    }

    @Test
    fun shouldDecreaseMaxSet() = runComposeUiTest {
        setUpRulesScreen()
        val decreaseSetsButton = onNodeWithTag(RulesScreenTags.DecreaseMaxSetsButton.tag)
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        decreaseSetsButton.performClick()
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.minus(1).toString())
    }

    @Test
    fun defaultMaxScoreShouldBeEleven() = runComposeUiTest {
        setUpRulesScreen()
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        //Max sets default value is 11
        maxScoreText.assertTextEquals(defaultMaxScore.toString())
    }

    @Test
    fun shouldIncreaseMaxScore() = runComposeUiTest {
        setUpRulesScreen()
        val increaseScoreButton = onNodeWithTag(RulesScreenTags.IncreaseMaxScoreButton.tag)
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        increaseScoreButton.performClick()
        //Max sets default value is 5
        maxScoreText.assertTextEquals(defaultMaxScore.plus(1).toString())
    }

    @Test
    fun shouldDecreaseMaxScore() = runComposeUiTest {
        setUpRulesScreen()
        val decreaseSetsButton = onNodeWithTag(RulesScreenTags.DecreaseMaxScoreButton.tag)
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        decreaseSetsButton.performClick()
        //Max sets default value is 5
        maxScoreText.assertTextEquals(defaultMaxScore.minus(1).toString())
    }
}