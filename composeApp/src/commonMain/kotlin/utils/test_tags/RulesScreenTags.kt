package utils.test_tags

sealed class RulesScreenTags(val tag: String) {
    data object MaxSetsText : RulesScreenTags("MaxSetsText")
    data object IncreaseMaxSetsButton : RulesScreenTags("IncreaseMaxSetsButton")
    data object DecreaseMaxSetsButton : RulesScreenTags("DecreaseMaxSetsButton")

    data object MaxScoreText : RulesScreenTags("MaxScoreText")
    data object IncreaseMaxScoreButton : RulesScreenTags("IncreaseMaxScoreButton")
    data object DecreaseMaxScoreButton : RulesScreenTags("DecreaseMaxScoreButton")

    data object WhoStartServingMenu : RulesScreenTags("WhoStartServingMenu")
}