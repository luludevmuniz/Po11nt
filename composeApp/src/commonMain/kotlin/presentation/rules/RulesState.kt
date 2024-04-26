package presentation.rules

import domain.model.Player
import domain.model.ServingSide

data class RulesState(
    val maxSets: Int = 5,
    val maxScore: Int = 11,
    val startServing: ServingSide = ServingSide.Left
)