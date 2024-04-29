package navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import domain.model.Player
import domain.model.ServingSide.Left
import presentation.game.GameEvent
import presentation.game.GameEvent.*
import presentation.game.GameScreen
import presentation.game.GameViewModel
import presentation.players.PlayersEvent
import presentation.players.PlayersScreen
import presentation.players.PlayersViewModel
import presentation.rules.RulesEvent
import presentation.rules.RulesScreen
import presentation.rules.RulesViewModel
import presentation.start.StartScreen
import utils.Orientation

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val windowSizeClass = calculateWindowSizeClass()
    var orientation by remember {
        mutableStateOf(
            if (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact) {
                Orientation.Horizontal
            } else {
                Orientation.Vertical
            }
        )
    }
    LaunchedEffect(windowSizeClass) {
        orientation = if (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact) {
            Orientation.Horizontal
        } else {
            Orientation.Vertical
        }
    }
    val playerOne = remember {
        Player()
    }
    val playerTwo = remember {
        Player()
    }
    var initialMaxSets by remember {
        mutableIntStateOf(0)
    }
    var initialMaxScore by remember {
        mutableIntStateOf(0)
    }
    var startServingSide by remember {
        mutableStateOf(Left)
    }
    val animDuration = 300
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(
            route = Screen.Start.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            }
        ) {
            StartScreen(orientation = orientation) {
                navController.navigate(Screen.Players.route)
            }
        }
        composable(
            route = Screen.Players.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            }
        ) {
            val viewModel = viewModel {
                PlayersViewModel()
            }
            PlayersScreen(
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onNextButtonClick = { playerOneName, playerTwoName ->
                    playerOne.name = playerOneName.ifBlank { "Player 1" }
                    playerTwo.name = playerTwoName.ifBlank { "Player 2" }
                    navController.navigate(Screen.Rules.route)
                },
                uiState = viewModel.uiState.collectAsState().value,
                onPlayerOneNameChanged = { name ->
                    viewModel.onEvent(PlayersEvent.OnPlayerOneNameChanged(name))
                },
                onPlayerTwoNameChanged = { name ->
                    viewModel.onEvent(PlayersEvent.OnPlayerTwoNameChanged(name))
                },
                orientation = orientation
            )
        }
        composable(
            route = Screen.Rules.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            }
        ) {
            val viewModel = viewModel {
                RulesViewModel()
            }
            RulesScreen(
                players = listOf(
                    playerOne,
                    playerTwo
                ),
                uiState = viewModel.uiState.collectAsState().value,
                onMaxSetsCounterChange = { sets ->
                    viewModel.onEvent(
                        RulesEvent.OnMaxSetsCounterChange(
                            sets
                        )
                    )
                },
                onMaxScoreCounterChange = { score ->
                    viewModel.onEvent(
                        RulesEvent.OnMaxScoreCounterChange(
                            score
                        )
                    )
                },
                onWhoStartServingChange = { servingSide ->
                    viewModel.onEvent(
                        RulesEvent.OnWhoStartServingChange(
                            servingSide
                        )
                    )
                },
                orientation = orientation,
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onNextButtonClick = { maxSets, maxScore, servingSide ->
                    initialMaxSets = maxSets
                    initialMaxScore = maxScore
                    startServingSide = servingSide
                    navController.navigate(Screen.Game.route)
                }
            )
        }
        composable(
            route = Screen.Game.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animDuration)
                )
            }
        ) {
            val playerOneColor = MaterialTheme.colorScheme.secondaryContainer
            val playerTwoColor = MaterialTheme.colorScheme.tertiaryContainer
            val viewModel by remember {
                mutableStateOf(
                    GameViewModel(
                        playerOne = playerOne.copy(color = playerOneColor),
                        playerTwo = playerTwo.copy(color = playerTwoColor),
                        maxScore = initialMaxScore,
                        maxSets = initialMaxSets,
                        startServingSide = startServingSide
                    )
                )
            }
            GameScreen(
                uiState = viewModel.uiState.collectAsState().value,
                orientation = orientation,
                onFinishClicked = {
                    navController.popBackStack(
                        route = Screen.Start.route,
                        inclusive = false,
                        saveState = false
                    )
                },
                onShowRestartModal = {
                    viewModel.onEvent(OnShowRestartModal)
                },
                onShowPlayersDialog = {
                    viewModel.onEvent(OnShowPlayersDialog)
                },
                onShowRulesDialog = {
                    viewModel.onEvent(OnShowRulesDialog)
                },
                onPlayerOneScored = {
                    viewModel.onEvent(
                        OnPlayerOneScoreChange(viewModel.uiState.value.playerOne.score.plus(1))
                    )
                },
                onPlayerTwoScored = {
                    viewModel.onEvent(
                        OnPlayerTwoScoreChange(viewModel.uiState.value.playerTwo.score.plus(1))
                    )
                },
                onPlayerOneScoreRollback = {
                    viewModel.onEvent(
                        OnPlayerOneScoreChange(viewModel.uiState.value.playerOne.score.minus(1))
                    )
                },
                onPlayerTwoScoreRollback = {
                    viewModel.onEvent(
                        OnPlayerTwoScoreChange(viewModel.uiState.value.playerTwo.score.minus(1))
                    )
                },
                onStartNextSet = {
                    viewModel.onEvent(OnStartNextSet)
                },
                onEndGameClicked = {
                    viewModel.onEvent(OnFinishGame)
                },
                onCloseEndDialog = {
                    viewModel.onEvent(OnStartNextSet)
                },
                onCloseSummaryDialog = {
                    viewModel.onEvent(OnCloseSummaryDialog)
                },
                onChangePlayersNames = { playerOneName, playerTwoName ->
                    viewModel.onEvent(OnPlayerOneNameChange(playerOneName))
                    viewModel.onEvent(OnPlayerTwoNameChange(playerTwoName))
                    viewModel.onEvent(OnClosePlayersDialog)
                },
                onClosePlayersDialog = {
                    viewModel.onEvent(OnClosePlayersDialog)
                },
                onChangeRules = { sets, score, servingSide ->
                    viewModel.onEvent(OnMaxSetsChange(sets))
                    viewModel.onEvent(OnMaxScoreChange(score))
                    viewModel.onEvent(OnServingSideChange(servingSide))
                    viewModel.onEvent(OnCloseRulesDialog)
                },
                onCloseRulesDialog = {
                    viewModel.onEvent(OnCloseRulesDialog)
                },
                onRestartGameClicked = {
                    viewModel.onEvent(OnRestartGame)
                    viewModel.onEvent(OnCloseRestartModal)
                },
                onCloseRestartModal = {
                    viewModel.onEvent(OnCloseRestartModal)
                },
                onSwitchButtonClick = {
                    viewModel.onEvent(OnSwitchPlayersSides)
                }
            )
        }
    }
}