package org.bandev.buddhaquotescompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotescompose.ui.theme.DarkBackground

@Composable
fun AppDrawer(
    navigateTo: (route: String) -> Unit,
    currentScreen: String,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBackground)) {
        DrawerButton(
            icon = Icons.Rounded.FormatQuote,
            label = "Home",
            isSelected = currentScreen == Scene.Home.route,
            action = {
                navigateTo(scenes[0].route)
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Rounded.FormatListBulleted,
            label = "Lists",
            isSelected = currentScreen == Scene.Lists.route,
            action = {
                navigateTo(scenes[1].route)
                closeDrawer()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Divider()
                DrawerButton(
                    icon = Icons.Rounded.Info,
                    label = "About",
                    isSelected = currentScreen == Scene.About.route,
                    action = {
                        navigateTo(scenes[2].route)
                        closeDrawer()
                    }
                )
                DrawerButton(
                    icon = Icons.Rounded.Settings,
                    label = "Settings",
                    isSelected = currentScreen == Scene.Settings.route,
                    action = {
                        navigateTo(scenes[3].route)
                        closeDrawer()
                    }
                )
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null, // decorative
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
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
