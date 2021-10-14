package org.bandev.buddhaquotescompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerValue(
    value: String,
    enabled: Boolean,
    modifier: Modifier,
    timeUnits: List<String>,
    onDelete: () -> Unit,
    onDeleteAll: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        value.chunked(2).forEachIndexed { index, s ->
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                val color =
                    if (enabled) MaterialTheme.colors.secondaryVariant else Color.Black
                Text(
                    text = s,
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.W400,
                        letterSpacing = 1.sp
                    ),
                    color = color
                )
                Text(
                    text = timeUnits[index],
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.W500),
                    modifier = Modifier.padding(bottom = 6.dp),
                    color = color
                )
            }
        }

        Icon(
            imageVector = Icons.Outlined.Backspace,
            contentDescription = null,
            tint = if (enabled) MaterialTheme.colors.secondaryVariant else Color.Black,
            modifier = Modifier
                .padding(start = 8.dp)
                .combinedClickable(
                    onClick = onDelete,
                    onLongClick = onDeleteAll,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 32.dp)
                )
        )
    }
}

@Composable
fun Dial(columns: List<List<String>>, onItemClick: (String) -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Row {
            columns.forEach {
                Column {
                    it.forEach {
                        DialItem(value = it, onItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun DialItem(value: String, onItemClick: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(120.dp)
            .height(85.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 60.dp),
                onClick = { onItemClick(value) }
            )
    ) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}