package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.alorma.settings.composables.SettingsMenuLink
import org.bandev.buddhaquotescompose.settings.Settings
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.ui.theme.DarkBackground

@Composable
fun SettingsScene(viewModel: SettingsViewModel = SettingsViewModel(LocalContext.current)) {

    val context = LocalContext.current

    // Get theme from ViewModel
    var theme by remember { mutableStateOf<Settings.Theme?>(null) }
    LaunchedEffect(key1 = Unit, block = { viewModel.getTheme { theme = it } })

    val darkTheme = isSystemInDarkTheme()
    var themeIcon by remember { mutableStateOf(if (darkTheme) Icons.Rounded.DarkMode else Icons.Rounded.LightMode)}

    Column {
        SettingsMenuLink(
            modifier = Modifier.background(DarkBackground),
            icon = { Icon(imageVector = themeIcon, contentDescription = "Wifi") },
            title = { Text(text = "Theme") },
            subtitle = { Text(text = theme?.name.toString()) },
            onClick = {
                      viewModel.setTheme(Settings.Theme.LIGHT)
            },
        )
    }
}