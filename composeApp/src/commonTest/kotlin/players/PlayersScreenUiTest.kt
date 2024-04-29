package players

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import presentation.players.PlayersScreen
import presentation.players.PlayersViewModel
import utils.Orientation
import utils.test_tags.PlayersScreenTags
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PlayersScreenUiTest {
    private val viewModel: PlayersViewModel = PlayersViewModel()

    private fun ComposeUiTest.setUpPlayersScreen() {
        setContent {
            PlayersScreen(
                onNavigationIconClick = {},
                onNextButtonClick = { _, _ -> },
                uiState = viewModel.uiState.value,
                onPlayerOneNameChanged = {},
                onPlayerTwoNameChanged = {},
                orientation = Orientation.Horizontal
            )
        }
    }

    @Test
    fun shouldAllComponentsBeVisible() = runComposeUiTest {
        setUpPlayersScreen()
        val playerOneTextfield = onNodeWithTag(PlayersScreenTags.PlayerOneTextField.tag)
        val playerTwoTextfield = onNodeWithTag(PlayersScreenTags.PlayerTwoTextField.tag)
        val nexyButton = onNodeWithTag(PlayersScreenTags.NextButton.tag)
        playerOneTextfield.assertExists()
        playerOneTextfield.assertIsEnabled()
        playerTwoTextfield.assertIsDisplayed()
        playerTwoTextfield.assertIsEnabled()
        nexyButton.assertIsDisplayed()
        nexyButton.assertIsEnabled()
        nexyButton.assertHasClickAction()
    }

    @Test
    fun shouldShowPlayersName() = runComposeUiTest {
        val playerOneName = "Lucas"
        val playerTwoName = "Gustavo"
        setUpPlayersScreen()
        val playerOneTextfield = onNodeWithTag(PlayersScreenTags.PlayerOneTextField.tag)
        val playerTwoTextfield = onNodeWithTag(PlayersScreenTags.PlayerTwoTextField.tag)
        playerOneTextfield.performTextInput(playerOneName)
        playerTwoTextfield.performTextInput(playerTwoName)
        playerOneTextfield.assertTextEquals(playerOneName)
        playerTwoTextfield.assertTextEquals(playerTwoName)
    }
}