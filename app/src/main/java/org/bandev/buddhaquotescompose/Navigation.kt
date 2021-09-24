package org.bandev.buddhaquotescompose

enum class SceneName { HOME, LISTS, INSIDELIST, DAILYQUOTE, SETTINGS, ABOUT  }

sealed class Scene(val id: SceneName, val route: String) {
    object Home : Scene(SceneName.HOME, "home")
    object Lists : Scene(SceneName.LISTS, "lists")
    object InsideList: Scene(SceneName.INSIDELIST, "insidelist")
    object DailyQuote : Scene(SceneName.DAILYQUOTE, "dailyquote")
    object Settings : Scene(SceneName.SETTINGS, "settings")
    object About: Scene(SceneName.ABOUT, "about")
}

val scenes = listOf(
    Scene.Home,
    Scene.Lists,
    Scene.InsideList,
    Scene.DailyQuote,
    Scene.Settings,
    Scene.About
)