package players

import App
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import utils.test_tags.PlayersScreenTags
import utils.test_tags.StartScreenTags
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PlayersScreenUiTest {

    @Test
    fun shouldShowPlayersName() = runComposeUiTest {
        val playerOneName = "Lucas"
        val playerTwoName = "Gustavo"
        setContent {
            App()
        }
        onNodeWithTag(StartScreenTags.NewGameButton.tag).performClick()
        val playerOneTextfield = onNodeWithTag(PlayersScreenTags.PlayerOneTextField.tag)
        val playerTwoTextfield = onNodeWithTag(PlayersScreenTags.PlayerTwoTextField.tag)
        playerOneTextfield.performTextInput(playerOneName)
        playerTwoTextfield.performTextInput(playerTwoName)
        playerOneTextfield.assertTextEquals(playerOneName)
        playerTwoTextfield.assertTextEquals(playerTwoName)
    }
}