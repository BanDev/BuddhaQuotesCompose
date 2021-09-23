package org.bandev.buddhaquotescompose.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness6
import androidx.compose.ui.graphics.vector.ImageVector
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.settings.Settings

data class Option(
    var icon: ImageVector = Icons.Rounded.Brightness6,
    var stringRes: Int = R.string.system,
    var theme: Settings.Theme = Settings.Theme.SYSTEM
)