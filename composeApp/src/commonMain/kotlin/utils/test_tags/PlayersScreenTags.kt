package utils.test_tags

sealed class PlayersScreenTags(val tag: String) {
    data object PlayerOneTextField : PlayersScreenTags("PlayerOneTextField")
    data object PlayerTwoTextField : PlayersScreenTags("PlayerTwoTextField")
    data object NextButton : PlayersScreenTags("NextButton")
}