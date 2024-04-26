import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import navigation.SetupNavGraph
import ui.theme.Po11ntTheme

@Composable
fun App() {
    Po11ntTheme {
        val navController = rememberNavController()
        SetupNavGraph(
            navController = navController
        )
    }
}