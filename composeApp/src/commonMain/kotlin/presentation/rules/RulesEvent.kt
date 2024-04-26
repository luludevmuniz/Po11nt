package presentation.rules

import domain.model.ServingSide

sealed interface RulesEvent {
    data class OnMaxSetsCounterChange(val value: Int) : RulesEvent
    data class OnMaxScoreCounterChange(val value: Int) : RulesEvent
    data class OnWhoStartServingChange(val value: ServingSide) : RulesEvent
}