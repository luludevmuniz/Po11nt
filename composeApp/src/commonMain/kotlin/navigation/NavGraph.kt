package navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import domain.model.Player
import domain.model.ServingSide.Left
import presentation.game.GameScreen
import presentation.players.PlayersScreen
import presentation.rules.RulesScreen
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
    var initalMaxSets by remember {
        mutableIntStateOf(0)
    }
    var initalMaxScore by remember {
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
            PlayersScreen(
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onNextButtonClick = { playerOneName, playerTwoName ->
                    playerOne.name = playerOneName.ifBlank { "Player 1" }
                    playerTwo.name = playerTwoName.ifBlank { "Player 2" }
                    navController.navigate(Screen.Rules.route)
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
            RulesScreen(
                players = listOf(
                    playerOne,
                    playerTwo
                ),
                orientation = orientation,
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onNextButtonClick = { maxSets, maxScore, servingSide ->
                    initalMaxSets = maxSets
                    initalMaxScore = maxScore
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
            }) {
            GameScreen(
                playerOne = playerOne,
                playerTwo = playerTwo,
                orientation = orientation,
                initalMaxScore = initalMaxScore,
                initalMaxSets = initalMaxSets,
                startServingSide = startServingSide,
                onFinishClicked = {
                    navController.popBackStack(
                        route = Screen.Start.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            )
        }
    }
}