package org.bandev.buddhaquotescompose.scenes


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import org.bandev.buddhaquotescompose.BuddhaQuotesApp
import org.bandev.buddhaquotescompose.MainActivity
import org.bandev.buddhaquotescompose.R

@ExperimentalAnimationApi
@Composable
fun SplashScene() {
    var visible by remember { mutableStateOf(false) }

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .background(colorResource(id = R.color.ic_launcher_background))
                    .fillMaxSize(),
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        initialAlpha = 0f,
                        animationSpec = tween(
                            durationMillis = MainActivity.splashFadeDurationMillis,
                            easing = CubicBezierEasing(0f, 0f, 0f, 1f)

                        )
                    ),
                ) {
                    BuddhaQuotesApp()
                }
            }
            LaunchedEffect(true) {
                visible = true
            }
        }
    )
}