import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import domain.model.Player
import domain.model.ServingSide.Left
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import navigation.Screen
import presentation.game.GameScreen
import presentation.players.PlayersScreen
import presentation.rules.RulesScreen
import presentation.start.StartScreen
import ui.theme.Po11ntTheme
import utils.Orientation

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App() {
    PreComposeApp {
        Po11ntTheme {
            val navigator = rememberNavigator()
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
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(
                        createTransition = slideInHorizontally(),
                        destroyTransition = slideOutHorizontally()
                    ),
                    initialRoute = Screen.Start.route
                ) {
                    scene(route = Screen.Start.route) {
                        StartScreen(orientation = orientation) {
                            navigator.navigate(Screen.Players.route)
                        }
                    }
                    scene(route = Screen.Players.route) {
                        PlayersScreen(
                            onNavigationIconClick = {
                                navigator.popBackStack()
                            },
                            onNextButtonClick = { playerOneName, playerTwoName ->
                                playerOne.name = playerOneName.ifBlank { "Player 1" }
                                playerTwo.name = playerTwoName.ifBlank { "Player 2" }
                                navigator.navigate(Screen.Rules.route)
                            },
                            orientation = orientation
                        )
                    }
                    scene(route = Screen.Rules.route) {
                        RulesScreen(
                            players = listOf(
                                playerOne,
                                playerTwo
                            ),
                            orientation = orientation,
                            onNavigationIconClick = {
                                navigator.popBackStack()
                            },
                            onNextButtonClick = { maxSets, maxScore, servingSide ->
                                initalMaxSets = maxSets
                                initalMaxScore = maxScore
                                startServingSide = servingSide
                                navigator.navigate(Screen.Game.route)
                            }
                        )
                    }
                    scene(route = Screen.Game.route) {
                        GameScreen(
                            playerOne = playerOne,
                            playerTwo = playerTwo,
                            orientation = orientation,
                            initalMaxScore = initalMaxScore,
                            initalMaxSets = initalMaxSets,
                            startServingSide = startServingSide,
                            onFinishClicked = {
                                navigator.goBack(
                                    popUpTo = PopUpTo(Screen.Start.route),
                                    inclusive = false
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}