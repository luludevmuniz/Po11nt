package presentation.players

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        PlayersState()
    )
    val uiState = _uiState.asStateFlow()
    
    fun onEvent(event: PlayersEvent) {
        when (event) {
            is PlayersEvent.OnPlayerOneNameChanged -> updatePlayerOneName(name = event.value)
            is PlayersEvent.OnPlayerTwoNameChanged -> updatePlayerTwoName(name = event.value)
        }
    }
    
    private fun updatePlayerOneName(name: String) {
        _uiState.update { playersState ->  
            playersState.copy(playerOneName = name) 
        }
    }
    
    private fun updatePlayerTwoName(name: String) {
        _uiState.update { playersState ->  
            playersState.copy(playerTwoName = name) 
        }
    }
}