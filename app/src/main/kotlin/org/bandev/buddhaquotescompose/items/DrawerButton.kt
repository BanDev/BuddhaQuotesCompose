package org.bandev.buddhaquotescompose.items

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerButton(
    val icon: ImageVector,
    val label: String = "",
    val isSelected: Boolean,
    val route: String,
    val modifier: Modifier = Modifier
)