package org.bandev.buddhaquotescompose.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness6
import androidx.compose.ui.graphics.vector.ImageVector
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.settings.Settings

data class Option(
    val icon: ImageVector = Icons.Rounded.Brightness6,
    val stringRes: Int = R.string.system,
    val theme: Settings.Theme = Settings.Theme.SYSTEM
)