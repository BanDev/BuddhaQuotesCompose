package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.alorma.settings.composables.SettingsMenuLink
import org.bandev.buddhaquotescompose.settings.Settings
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.boolify
import org.bandev.buddhaquotescompose.settings.stringify

@Composable
fun SettingsScene(viewModel: SettingsViewModel = SettingsViewModel(LocalContext.current)) {

    val openDialog = remember { mutableStateOf(false) }

    val theme = viewModel.getThemeLive()

    val darkTheme = theme.boolify()

    val themeIcon by remember { mutableStateOf(if (darkTheme) Icons.Rounded.DarkMode else Icons.Rounded.LightMode)}

    Column {
        SettingsMenuLink(
            icon = { Icon(imageVector = themeIcon, contentDescription = "Wifi") },
            title = { Text(text = "Theme") },
            subtitle = { Text(text = theme.stringify()) },
            onClick = {
                openDialog.value = true
            },
        )
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Hi")
            },
            text = {
                Text(
                    "idk if material compose has the radio buttons thingy in here yet, so this will have to do for now :)"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        viewModel.setTheme(Settings.Theme.LIGHT)
                    }
                ) {
                    Text("Light")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        viewModel.setTheme(Settings.Theme.DARK)
                    }
                ) {
                    Text("Dark")
                }
            }
        )
    }
}