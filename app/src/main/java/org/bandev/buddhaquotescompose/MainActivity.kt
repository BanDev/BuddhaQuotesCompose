package org.bandev.buddhaquotescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.scenes.*
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.boolify
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme
import org.bandev.buddhaquotescompose.ui.theme.EdgeToEdgeContent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val splashWasDisplayed = savedInstanceState != null
        if (!splashWasDisplayed) {
            val splashScreen = installSplashScreen()

            splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
                splashScreenViewProvider.iconView
                    .animate()
                    .setDuration(splashFadeDurationMillis.toLong())
                    .alpha(0f)
                    .withEndAction {
                        splashScreenViewProvider.remove()
                        setContent{
                            SplashScene()
                        }
                    }.start()
            }
        } else {
            setTheme(R.style.Theme_BuddhaQuotesCompose)
            setContent {
                SplashScene()
            }
        }
    }

    companion object {
        const val splashFadeDurationMillis = 300
    }
}