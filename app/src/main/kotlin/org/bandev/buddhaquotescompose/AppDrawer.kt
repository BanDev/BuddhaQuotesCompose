package org.bandev.buddhaquotescompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotescompose.items.DrawerButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    navigateTo: (route: String) -> Unit,
    currentScreen: String,
    closeDrawer: () -> Unit
) {
    val topDrawerButtons = listOf(
        DrawerButton(
            icon = Icons.Rounded.FormatQuote,
            label = "Home",
            isSelected = currentScreen == Scene.Home.route,
            route = scenes[0].route
        ),
        DrawerButton(
            icon = Icons.Rounded.FormatListBulleted,
            label = "Lists",
            isSelected = currentScreen == Scene.Lists.route,
            route = scenes[1].route
        ),
        DrawerButton(
            icon = Icons.Rounded.CalendarToday,
            label = "Daily Quote",
            isSelected = currentScreen == Scene.DailyQuote.route,
            route = scenes[3].route
        ),
        DrawerButton(
            icon = Icons.Rounded.SelfImprovement,
            label = "Meditate",
            isSelected = currentScreen == Scene.Meditate.route,
            route = scenes[4].route
        )
    )

    val bottomDrawerButtons = listOf(
        DrawerButton(
            icon = Icons.Rounded.Settings,
            label = "Settings",
            isSelected = currentScreen == Scene.Settings.route,
            route = scenes[5].route
        ),
        DrawerButton(
            icon = Icons.Rounded.Info,
            label = "About",
            isSelected = currentScreen == Scene.About.route,
            route = scenes[6].route
        )
    )

    ModalDrawerSheet {
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
fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
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
                modifier = Modifier.fillMaxWidth().height(30.dp)
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = textIconColor
                )
            }
        }
    }
}
