package presentation.game.middle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.BlackPearl

@Composable
internal fun Scoreboard(
    modifier: Modifier = Modifier,
    currentPlayerOneScore: Int,
    currentPlayerTwoScore: Int,
    onCurrentPlayerOneScoreChanged: () -> Unit,
    onCurrentPlayerTwoScoreChanged: () -> Unit,
    shouldRollback: Pair<Boolean, Boolean>,
    onRollBack: (Pair<Boolean, Boolean>) -> Unit,
) {
    Row(
        modifier = modifier
    ) {
        ScoreBox(
            currentScore = currentPlayerOneScore,
            onCurrentScoreChanged = { onCurrentPlayerOneScoreChanged() },
            shouldRollback = shouldRollback.first,
            onRollBack = {
                onRollBack(
                    Pair(
                        shouldRollback.first.not(),
                        shouldRollback.second
                    )
                )
            }
        )
        ScoreBox(
            currentScore = currentPlayerTwoScore,
            onCurrentScoreChanged = { onCurrentPlayerTwoScoreChanged() },
            shouldRollback = shouldRollback.second,
            onRollBack = {
                Pair(
                    shouldRollback.first,
                    shouldRollback.second.not()
                )
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.ScoreBox(
    currentScore: Int,
    onCurrentScoreChanged: () -> Unit,
    shouldRollback: Boolean,
    onRollBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .weight(0.5f)
            .border(
                width = 0.5.dp,
                color = BlackPearl
            ),
        contentAlignment = Alignment.Center
    ) {
        val lazyListState = rememberLazyListState()
        val userScrollEnabled = remember {
            mutableStateOf(true)
        }
        val items = remember {
            listOf(0, 1).toMutableStateList()
        }
        val currentVisibleItem by remember {
            derivedStateOf {
                val layoutInfo = lazyListState.layoutInfo
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()
                if (visibleItemsInfo.isEmpty()) {
                    0
                } else {
                    val lastItem = fullyVisibleItemsInfo.last()

                    val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                    if (lastItem.offset + lastItem.size > viewportHeight) {
                        fullyVisibleItemsInfo.removeLast()
                    }

                    val firstItemIfLeft = fullyVisibleItemsInfo.firstOrNull()
                    
                    if (firstItemIfLeft != null && firstItemIfLeft.offset < layoutInfo.viewportStartOffset) {
                        fullyVisibleItemsInfo.removeFirst()
                    }
                    fullyVisibleItemsInfo.firstOrNull()?.index ?: 0
                }
            }
        }

        LaunchedEffect(
            lazyListState.isScrollInProgress,
            currentVisibleItem
        ) {
            if (lazyListState.isScrollInProgress.not() && currentVisibleItem == 1) {
                userScrollEnabled.value = false
                items.removeFirst()
                items.add(items.first().plus(1))
                lazyListState.scrollToItem(0)
                onCurrentScoreChanged()
                userScrollEnabled.value = true
            }
        }

        SideEffect {
            if (userScrollEnabled.value && currentVisibleItem == 0) {
                userScrollEnabled.value = false
                items.removeAll(items)
                items.add(currentScore)
                items.add(currentScore.plus(1))
                userScrollEnabled.value = true

            }
        }

        if (shouldRollback) {
            if (items.first() != 0) {
                items.add(0, items.first().minus(1))
                items.removeLast()
            }
            onRollBack()
        }

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            flingBehavior = rememberSnapFlingBehavior(lazyListState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            state = lazyListState,
            userScrollEnabled = userScrollEnabled.value
        ) {
            items(items = items) { item ->
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight(
                                align = Alignment.CenterVertically,
                                unbounded = true
                            ),
                        textAlign = TextAlign.Center,
                        text = item.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}