package org.bandev.buddhaquotescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BuddhaQuotesCompose)
        setContent {
            BuddhaQuotesApp()
        }
    }

    companion object {
        const val splashFadeDurationMillis = 300
    }
}