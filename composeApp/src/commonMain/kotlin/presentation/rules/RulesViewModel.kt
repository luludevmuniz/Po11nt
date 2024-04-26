package presentation.rules

import androidx.lifecycle.ViewModel
import domain.model.ServingSide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RulesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RulesState())
    val uiState = _uiState.asStateFlow()
    
    fun onEvent(event: RulesEvent) {
        when (event) {
            is RulesEvent.OnMaxSetsCounterChange -> updateMaxSets(sets = event.value)
            is RulesEvent.OnMaxScoreCounterChange -> updateMaxScore(score = event.value)
            is RulesEvent.OnWhoStartServingChange -> updateWhoStartServing(servingSide = event.value)
        }
    }
    
    private fun updateMaxSets(sets: Int) {
        _uiState.update { state ->
            state.copy(maxSets = sets)
        }
    }
    
    private fun updateMaxScore(score: Int) {
        _uiState.update { state ->
            state.copy(maxScore = score)
        }
    }
    
    private fun updateWhoStartServing(servingSide: ServingSide) {
        _uiState.update { state ->
            state.copy(startServing = servingSide)
        }
    }
}