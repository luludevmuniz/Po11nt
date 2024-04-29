package domain.model

import androidx.compose.ui.graphics.Color

data class Player(
    var name: String = "",
    var sets: Int = 0,
    var score: Int = 0,
    var color: Color? = null
)