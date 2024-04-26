package utils.test_tags

sealed class StartScreenTags(val tag: String) {
    data object NewGameButton : StartScreenTags("NewGameButton")
}