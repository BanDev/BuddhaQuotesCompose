package org.bandev.buddhaquotescompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import org.bandev.buddhaquotescompose.items.DrawerButtonData

class DrawerButtonList(currentScreen: String) {
    val topDrawerButtons = arrayOf(
        DrawerButtonData(
            icon = Icons.Rounded.FormatQuote,
            label = "Home",
            isSelected = currentScreen == Scene.Home.route,
            route = scenes[0].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.CalendarToday,
            label = "Daily Quote",
            isSelected = currentScreen == Scene.DailyQuote.route,
            route = scenes[1].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.Favorite,
            label = "Favourites",
            isSelected = currentScreen == Scene.Favourites.route,
            route = scenes[2].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.FormatListBulleted,
            label = "Lists",
            isSelected = currentScreen == Scene.Lists.route,
            route = scenes[3].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.SelfImprovement,
            label = "Meditate",
            isSelected = currentScreen == Scene.Meditate.route,
            route = scenes[5].route
        )
    )

    val bottomDrawerButtons = arrayOf(
        DrawerButtonData(
            icon = Icons.Rounded.Settings,
            label = "Settings",
            isSelected = currentScreen == Scene.Settings.route,
            route = scenes[6].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.Info,
            label = "About",
            isSelected = currentScreen == Scene.About.route,
            route = scenes[7].route
        )
    )
}