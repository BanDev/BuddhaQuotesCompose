package org.bandev.buddhaquotescompose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.ui.theme.SpringDefaultDampingRatio
import org.bandev.buddhaquotescompose.ui.theme.SpringDefaultStiffness
import kotlin.math.roundToInt

@Composable
internal fun Timer(
    modifier: Modifier,
    timerDurationInMillis: Int,
    onTimerDurationInMillisChange: (Int) -> Unit,
    isTimerStarted: Boolean,
    onTimerIsStartedChange: (Boolean) -> Unit,
    timerProgress: Float,
) {
    val cardHorizontalPadding by animateDpAsState(
        targetValue = if (isTimerStarted) 48.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = SpringDefaultDampingRatio,
            stiffness = SpringDefaultStiffness
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = cardHorizontalPadding)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Crossfade(
                targetState = isTimerStarted,
                animationSpec = tween(durationMillis = if (isTimerStarted) 0 else 350),
                modifier = Modifier.animateContentHeight()
            ) { isTimerStartedCurrentValue ->
                if (!isTimerStartedCurrentValue) {
                    TimePicker(
                        seconds = timerDurationInMillis / 1000,
                        onSecondsChange = { seconds ->
                            onTimerDurationInMillisChange(seconds * 1000)
                        }
                    )
                } else {
                    TimerCountdown(
                        timerProgress = timerProgress,
                        timerDurationInMillis = timerDurationInMillis
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            StartCancelTimerButton(
                isTimerStarted = isTimerStarted,
                enabled = timerDurationInMillis > 0
            ) {
                onTimerIsStartedChange(!isTimerStarted)
            }

        }
    }
}

@Composable
private fun TimerCountdown(timerProgress: Float, timerDurationInMillis: Int) {
    val style = remember {
        TextStyle(fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)
    }
    val text = remember(timerProgress, timerDurationInMillis) {
        val seconds = (timerProgress * (timerDurationInMillis / 1000)).roundToInt()
        String.format("%02d : %02d", seconds / 60, seconds % 60)
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        style = style,
        text = text
    )
}

@Composable
private fun StartCancelTimerButton(isTimerStarted: Boolean, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        border = if (isTimerStarted) ButtonDefaults.outlinedButtonBorder else null,
        colors = if (!isTimerStarted) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
        elevation = if (isTimerStarted) null else ButtonDefaults.buttonElevation()
    ) {
        Text(text = if (!isTimerStarted) "START" else "CANCEL")
    }
}

@Composable
private fun TimePicker(
    modifier: Modifier = Modifier,
    seconds: Int,
    onSecondsChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Counter(
            label = "Minutes",
            value = seconds / 60,
            onValueChange = { m -> onSecondsChange(m.coerceAtLeast(0) * 60 + seconds % 60) }
        )
        Spacer(Modifier.height(16.dp))
        Counter(
            label = "Seconds",
            value = seconds % 60,
            onValueChange = { s -> onSecondsChange((seconds - seconds % 60) + s.coerceIn(0, 59)) }
        )
    }
}

@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = String.format("%02d", value),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onValueChange(value + 1) }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Increment $label"
                )
            }

            IconButton(onClick = { onValueChange(value - 1) }) {
                Icon(
                    imageVector = Icons.Rounded.Remove,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Decrease $label"
                )
            }
        }
    }
}

private fun Modifier.animateContentHeight(): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "animateContentSize"
    }
) {
    val scope = rememberCoroutineScope()
    val animModifier = remember(scope) { HeightAnimationModifier(scope) }
    this
        .clipToBounds()
        .then(animModifier)
}

private class HeightAnimationModifier(val scope: CoroutineScope) : LayoutModifier {
    val animSpec: AnimationSpec<Int> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    private data class AnimData(
        val anim: Animatable<Int, AnimationVector1D>,
        var startHeight: Int
    )

    private var animData: AnimData? = null

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val placeable = measurable.measure(constraints)

        val measuredSize = IntSize(placeable.width, placeable.height)
        val width = measuredSize.width
        val height = animateTo(measuredSize.height)
        return layout(width, height) {
            placeable.placeRelative(0, 0)
        }
    }

    fun animateTo(targetHeight: Int): Int {
        val data = animData?.apply {
            if (targetHeight != anim.targetValue) {
                startHeight = anim.value
                scope.launch {
                    anim.animateTo(targetHeight, animSpec)
                }
            }
        } ?: AnimData(Animatable(targetHeight, Int.VectorConverter, 1), targetHeight)
        animData = data
        return data.anim.value
    }
}