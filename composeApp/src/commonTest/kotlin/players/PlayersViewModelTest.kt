import presentation.players.PlayersViewModel
import presentation.players.PlayersEvent.OnPlayerOneNameChanged
import presentation.players.PlayersEvent.OnPlayerTwoNameChanged
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayersViewModelTest {
    private val viewModel: PlayersViewModel = PlayersViewModel()
    
    @Test
    fun shouldUpdatePlayerOneName() {
        val playerOneName = "Lucas"
        viewModel.onEvent(OnPlayerOneNameChanged(playerOneName))
        assertEquals(playerOneName, viewModel.uiState.value.playerOneName)
    }

    @Test
    fun shouldUpdatePlayerTwoName() {
        val playerTwoName = "Gustavo"
        viewModel.onEvent(OnPlayerTwoNameChanged(playerTwoName))
        assertEquals(playerTwoName, viewModel.uiState.value.playerTwoName)
    }
}