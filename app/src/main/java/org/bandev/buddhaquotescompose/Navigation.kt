package org.bandev.buddhaquotescompose

enum class SceneName { HOME, DAILYQUOTE, FAVOURITES , LISTS, INSIDELIST, MEDITATE, SETTINGS, ABOUT  }

sealed class Scene(val id: SceneName, val route: String) {
    object Home : Scene(SceneName.HOME, "home")
    object DailyQuote : Scene(SceneName.DAILYQUOTE, "dailyquote")
    object Favourites : Scene(SceneName.FAVOURITES, "favourites")
    object Lists : Scene(SceneName.LISTS, "lists")
    object InsideList: Scene(SceneName.INSIDELIST, "insidelist")
    object Meditate: Scene(SceneName.MEDITATE, "meditate")
    object Settings : Scene(SceneName.SETTINGS, "settings")
    object About: Scene(SceneName.ABOUT, "about")
}

val scenes = listOf(
    Scene.Home,
    Scene.DailyQuote,
    Scene.Favourites,
    Scene.Lists,
    Scene.InsideList,
    Scene.Meditate,
    Scene.Settings,
    Scene.About
)