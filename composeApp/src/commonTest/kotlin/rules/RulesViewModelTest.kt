package rules

import domain.model.ServingSide.Left
import presentation.rules.RulesEvent.OnMaxScoreCounterChange
import presentation.rules.RulesEvent.OnMaxSetsCounterChange
import presentation.rules.RulesEvent.OnWhoStartServingChange
import presentation.rules.RulesViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

class RulesViewModelTest {
    private val viewModel = RulesViewModel()

    @Test
    fun shouldUpdateMaxSets() {
        val maxSets = 10
        viewModel.onEvent(OnMaxSetsCounterChange(maxSets))
        assertEquals(maxSets, viewModel.uiState.value.maxSets)
    }

    @Test
    fun shouldUpdateMaxScore() {
        val maxScore = 21
        viewModel.onEvent(OnMaxScoreCounterChange(maxScore))
        assertEquals(maxScore, viewModel.uiState.value.maxScore)
    }

    @Test
    fun shouldUpdateWhoStartServing() {
        val startServingSide = Left
        viewModel.onEvent(OnWhoStartServingChange(startServingSide))
        assertEquals(startServingSide, viewModel.uiState.value.startServing)
    }
}