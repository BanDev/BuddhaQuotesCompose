package org.bandev.buddhaquotescompose.items

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerButtonData(
    val icon: ImageVector,
    val label: String = "",
    val isSelected: Boolean,
    val route: String,
    val modifier: Modifier = Modifier
)