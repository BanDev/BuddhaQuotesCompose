package org.bandev.buddhaquotescompose.ui.theme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

val Tabs = Shapes(
    small = RoundedCornerShape(topEnd = 11.dp, topStart = 11.dp),
    medium = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp),
    large = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
)

/**
 * A medium tab
 */

fun Modifier.mediumTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed {
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabPosition.width / 2 - 120.dp / 2,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing, delayMillis = 0)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(120.dp)
        .height(3.dp)
        .clip(Tabs.medium)
}
