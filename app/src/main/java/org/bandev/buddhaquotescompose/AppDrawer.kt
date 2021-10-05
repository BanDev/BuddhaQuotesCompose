package org.bandev.buddhaquotescompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import org.bandev.buddhaquotescompose.items.DrawerButtonData

@Composable
fun AppDrawer(
    navigateTo: (route: String) -> Unit,
    currentScreen: String,
    closeDrawer: () -> Unit
) {
    val topDrawerButtons = arrayOf(
        DrawerButtonData(
            icon = Icons.Rounded.FormatQuote,
            label = "Home",
            isSelected = currentScreen == Scene.Home.route,
            route = scenes[0].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.FormatListBulleted,
            label = "Lists",
            isSelected = currentScreen == Scene.Lists.route,
            route = scenes[1].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.CalendarToday,
            label = "Daily Quote",
            isSelected = currentScreen == Scene.DailyQuote.route,
            route = scenes[3].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.SelfImprovement,
            label = "Meditate",
            isSelected = currentScreen == Scene.Meditate.route,
            route = scenes[4].route
        )
    )

    val bottomDrawerButtons = arrayOf(
        DrawerButtonData(
            icon = Icons.Rounded.Settings,
            label = "Settings",
            isSelected = currentScreen == Scene.Settings.route,
            route = scenes[5].route
        ),
        DrawerButtonData(
            icon = Icons.Rounded.Info,
            label = "About",
            isSelected = currentScreen == Scene.About.route,
            route = scenes[6].route
        )
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(topDrawerButtons) { drawerButton ->
                DrawerButton(
                    icon = drawerButton.icon,
                    label = drawerButton.label,
                    isSelected = drawerButton.isSelected,
                    action = {
                        navigateTo(drawerButton.route)
                        closeDrawer()
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Divider()
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(bottomDrawerButtons) { drawerButton ->
                        DrawerButton(
                            icon = drawerButton.icon,
                            label = drawerButton.label,
                            isSelected = drawerButton.isSelected,
                            action = {
                                navigateTo(drawerButton.route)
                                closeDrawer()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) 1f else 0.6f
    val textIconColor = if (isSelected) colors.primary else colors.onSurface.copy(alpha = 0.6f)
    val backgroundColor = if (isSelected) colors.primary.copy(alpha = 0.12f) else Color.Transparent
    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null, // decorative
                    modifier = Modifier.alpha(imageAlpha),
                    tint = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}
