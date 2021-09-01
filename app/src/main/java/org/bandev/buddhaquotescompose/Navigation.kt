package org.bandev.buddhaquotescompose

enum class SceneName { HOME, ABOUT, SETTINGS }

sealed class Scene(val id: SceneName, val route: String) {
    object Home : Scene(SceneName.HOME, "home")
    object About: Scene(SceneName.ABOUT, "about")
    object Settings : Scene(SceneName.SETTINGS, "settings")
}

val scenes = listOf(
    Scene.Home,
    Scene.About,
    Scene.Settings
)