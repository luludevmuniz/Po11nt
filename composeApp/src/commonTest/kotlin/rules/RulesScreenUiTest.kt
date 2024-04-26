package rules

import App
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import utils.test_tags.PlayersScreenTags
import utils.test_tags.RulesScreenTags
import utils.test_tags.StartScreenTags
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class RulesScreenUiTest {
    private val defaultMaxSets = 5
    private val defaultMaxScore = 11

    @Test
    fun defaultMaxSetsShouldBeFive() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.toString())
    }

    @Test
    fun shouldIncreaseMaxSet() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val increaseSetsButton = onNodeWithTag(RulesScreenTags.IncreaseMaxSetsButton.tag)
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        increaseSetsButton.performClick()
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.plus(1).toString())
    }

    @Test
    fun shouldDecreaseMaxSet() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val decreaseSetsButton = onNodeWithTag(RulesScreenTags.DecreaseMaxSetsButton.tag)
        val maxSetsText = onNodeWithTag(RulesScreenTags.MaxSetsText.tag)
        decreaseSetsButton.performClick()
        //Max sets default value is 5
        maxSetsText.assertTextEquals(defaultMaxSets.minus(1).toString())
    }

    @Test
    fun defaultMaxScoreShouldBeEleven() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        //Max sets default value is 11
        maxScoreText.assertTextEquals(defaultMaxScore.toString())
    }

    @Test
    fun shouldIncreaseMaxScore() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val increaseScoreButton = onNodeWithTag(RulesScreenTags.IncreaseMaxScoreButton.tag)
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        increaseScoreButton.performClick()
        //Max sets default value is 5
        maxScoreText.assertTextEquals(defaultMaxScore.plus(1).toString())
    }

    @Test
    fun shouldDecreaseMaxScore() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        onNodeWithTag(PlayersScreenTags.NextButton.tag).performClick()
        val decreaseSetsButton = onNodeWithTag(RulesScreenTags.DecreaseMaxScoreButton.tag)
        val maxScoreText = onNodeWithTag(RulesScreenTags.MaxScoreText.tag)
        decreaseSetsButton.performClick()
        //Max sets default value is 5
        maxScoreText.assertTextEquals(defaultMaxScore.minus(1).toString())
    }
}