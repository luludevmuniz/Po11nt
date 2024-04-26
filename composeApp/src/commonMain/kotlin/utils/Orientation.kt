package utils

sealed interface Orientation {
    data object Horizontal: Orientation
    data object Vertical: Orientation
}