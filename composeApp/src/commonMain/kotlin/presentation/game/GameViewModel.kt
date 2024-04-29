package presentation.game

import androidx.lifecycle.ViewModel
import domain.model.Player
import domain.model.ServingSide
import domain.model.ServingSide.Left
import domain.model.ServingSide.Right
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(
    playerOne: Player, playerTwo: Player, startServingSide: ServingSide, maxScore: Int, maxSets: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        GameState(
            playerOne = playerOne,
            playerTwo = playerTwo,
            servingSide = startServingSide,
            maxScore = maxScore,
            maxSets = maxSets
        )
    )
    val uiState = _uiState.asStateFlow()
    private var _deuceExtraPoints = 0
    private val _isDeuce get() = _deuceExtraPoints != 0
    private var _setStartServingSide = startServingSide
    private val _setWinner: Player?
        get() = when (_uiState.value.maxScore) {
            _uiState.value.playerOne.score -> _uiState.value.playerOne
            _uiState.value.playerTwo.score -> _uiState.value.playerTwo
            else -> null
        }
    private val _gameWinner: Player
        get() = if (_uiState.value.playerOne.sets > _uiState.value.playerTwo.sets) {
            _uiState.value.playerOne
        } else {
            _uiState.value.playerTwo
        }

    private val _gameLoser: Player
        get() = if (_uiState.value.playerOne.sets > _uiState.value.playerTwo.sets) {
            _uiState.value.playerTwo
        } else {
            _uiState.value.playerOne
        }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnMaxScoreChange -> changeMaxScore(score = event.value)
            is GameEvent.OnMaxSetsChange -> changeMaxSets(sets = event.value)
            is GameEvent.OnPlayerOneNameChange -> changePlayerOneName(name = event.value)
            is GameEvent.OnPlayerOneScoreChange -> changePlayerOneScore(score = event.value)
            is GameEvent.OnPlayerTwoNameChange -> changePlayerTwoName(name = event.value)
            is GameEvent.OnPlayerTwoScoreChange -> changePlayerTwoScore(score = event.value)
            is GameEvent.OnServingSideChange -> changeServingSide(side = event.value)
            GameEvent.OnStartNextSet -> startNextSet()
            GameEvent.OnFinishGame -> finishGame()
            GameEvent.OnShowRestartModal -> showRestartModal()
            GameEvent.OnCloseRestartModal -> closeRestartModal()
            GameEvent.OnShowPlayersDialog -> showPlayersDialog()
            GameEvent.OnClosePlayersDialog -> closePlayersDialog()
            GameEvent.OnShowRulesDialog -> showRulesDialog()
            GameEvent.OnCloseRulesDialog -> closeRulesDialog()
            GameEvent.OnRestartGame -> restartGame()
            GameEvent.OnCloseSummaryDialog -> closeSummaryDialog()
        }
    }

    private fun changeMaxScore(score: Int) {
        _uiState.update { state ->
            state.copy(
                maxScore = score
            )
        }
    }

    private fun changeMaxSets(sets: Int) {
        _uiState.update { state ->
            state.copy(maxSets = sets)
        }
    }

    private fun changePlayerOneName(name: String) {
        _uiState.update { state ->
            state.copy(
                playerOne = state.playerOne.copy(name = name)
            )
        }
    }

    private fun changePlayerOneScore(score: Int) {
        _uiState.update { state ->
            state.copy(
                playerOne = state.playerOne.copy(score = score),
                maxScore = if (_isDeuce && _uiState.value.playerTwo.score > score) {
                    _deuceExtraPoints--
                    _uiState.value.maxScore.minus(1)
                } else {
                    _uiState.value.maxScore
                }
            )
        }
        checkForDeuce()
        updateServingSide()
        _setWinner?.let { winner ->
            endSet(winner = winner)
        }
    }

    private fun changePlayerTwoName(name: String) {
        _uiState.update { state ->
            state.copy(
                playerTwo = state.playerTwo.copy(name = name)
            )
        }
    }

    private fun changePlayerTwoScore(score: Int) {
        _uiState.update { state ->
            state.copy(
                playerTwo = state.playerTwo.copy(score = score),
                maxScore = if (_isDeuce && _uiState.value.playerOne.score > score) {
                    _deuceExtraPoints--
                    _uiState.value.maxScore.minus(1)
                } else {
                    _uiState.value.maxScore
                }
            )
        }
        checkForDeuce()
        updateServingSide()
        _setWinner?.let { winner ->
            endSet(winner = winner)
        }
    }

    private fun checkForDeuce() {
        _uiState.value.apply {
            if (playerOne.score.plus(1) == maxScore && playerTwo.score.plus(1) == maxScore) {
                _uiState.update { state ->
                    _deuceExtraPoints++
                    state.copy(maxScore = state.maxScore.plus(1))
                }
            }
        }
    }

    private fun updateServingSide() {
        val servingSide = _uiState.value.servingSide
        if (_isDeuce) {
            _uiState.update { state ->
                state.copy(servingSide = if (servingSide == Left) Right else Left)
            }
        } else {
            val totalScore = _uiState.value.playerOne.score + _uiState.value.playerTwo.score
            when {
                totalScore % 4 == 0 || totalScore in 1.._uiState.value.maxScore.times(2) step 4 -> {
                    _uiState.update { state ->
                        state.copy(servingSide = _setStartServingSide)
                    }
                }

                else -> _uiState.update { state ->
                    state.copy(servingSide = if (_setStartServingSide == Left) Right else Left)
                }
            }
        }
    }

    private fun changeServingSide(side: ServingSide) {
        _uiState.update { state ->
            state.copy(
                servingSide = side
            )
        }
    }

    private fun endSet(winner: Player) {
        _uiState.update { state ->
            if (state.playerOne.score == state.maxScore) {
                state.copy(
                    playerOne = winner.copy(
                        sets = winner.sets.plus(1),
                        score = 0
                    ),
                    playerTwo = state.playerTwo.copy(score = 0),
                    maxScore = state.maxScore.minus(_deuceExtraPoints),
                    setEnded = true
                )
            } else {
                state.copy(
                    playerTwo = winner.copy(
                        sets = winner.sets.plus(1),
                        score = 0
                    ),
                    playerOne = state.playerOne.copy(score = 0),
                    maxScore = state.maxScore.minus(_deuceExtraPoints),
                    setEnded = true
                )
            }
        }
        _deuceExtraPoints = 0
    }

    private fun startNextSet() {
        _setStartServingSide = if (_setStartServingSide == Left) Right else Left
        _uiState.update { state ->
            state.copy(
                setEnded = false,
                servingSide = _setStartServingSide
            )
        }
        if (_uiState.value.playerOne.sets.times(2) >= _uiState.value.maxSets || _uiState.value.playerTwo.sets.times(
                2
            ) >= _uiState.value.maxSets
        ) {
            finishGame()
        }
    }

    private fun finishGame() {
        _uiState.update { state ->
            state.copy(
                setEnded = false,
                showSummaryDialog = true,
                winner = _gameWinner,
                loser = _gameLoser
            )
        }
    }

    private fun showPlayersDialog() {
        _uiState.update { state ->
            state.copy(showPlayersDialog = true)
        }
    }

    private fun closePlayersDialog() {
        _uiState.update { state ->
            state.copy(showPlayersDialog = false)
        }
    }

    private fun showRulesDialog() {
        _uiState.update { state ->
            state.copy(showRulesDialog = true)
        }
    }

    private fun closeRulesDialog() {
        _uiState.update { state ->
            state.copy(showRulesDialog = false)
        }
    }

    private fun showRestartModal() {
        _uiState.update { state ->
            state.copy(showRestartModal = true)
        }
    }

    private fun closeRestartModal() {
        _uiState.update { state ->
            state.copy(showRestartModal = false)
        }
    }

    private fun restartGame() {
        _uiState.update { state ->
            state.copy(
                playerOne = state.playerOne.copy(
                    score = 0,
                    sets = 0
                ),
                playerTwo = state.playerTwo.copy(
                    score = 0,
                    sets = 0
                ),
                servingSide = _setStartServingSide
            )
        }
    }

    private fun closeSummaryDialog() {
        _uiState.update { state ->
            state.copy(showSummaryDialog = false)
        }
    }
}