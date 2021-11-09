package org.bandev.buddhaquotescompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.scenes.*
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.boolify
import org.bandev.buddhaquotescompose.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BuddhaQuotesApp(
    viewModel: BuddhaQuotesViewModel = viewModel(),
) {
    val settings = SettingsViewModel(LocalContext.current)

    BuddhaQuotesComposeTheme(darkTheme = settings.getThemeLive().boolify()) {
        EdgeToEdgeContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Scene.Home.route
            var quote by remember { mutableStateOf(Quote(1, R.string.blank, false)) }
            LaunchedEffect(
                key1 = Unit,
                block = {
                    viewModel.Quotes().getRandom { quote = it }
                }
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground),
            ) {
                NavHost(navController = navController, startDestination = Scene.Home.route) {
                    composable(Scene.Home.route) { HomeScene() }
                    composable(Scene.DailyQuote.route) { DailyQuoteScene() }
                    composable(Scene.Settings.route) { SettingsScene() }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Card(
                        modifier = Modifier
                            .padding(20.dp)
                            .wrapContentSize(),
                        shape = RoundedCornerShape(14.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            scenes.forEach { scene ->
                                BottomNavButton(
                                    modifier = Modifier.padding(2.dp),
                                    onClick = {
                                        navController.navigate(scene.route)
                                    },
                                    isSelected = currentRoute == scene.route,
                                    icon = Icons.Rounded.Nature
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavButton(
    modifier: Modifier,
    onClick: () -> Unit,
    isSelected: Boolean,
    icon: ImageVector
) {
    var visibility by remember { mutableStateOf(isSelected) }
    IconButton(
        onClick = {
            visibility = isSelected
            run(onClick)
                  },
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            AnimatedVisibility(visible = visibility) {
                Icon(imageVector = Icons.Rounded.Circle, contentDescription = null, modifier = Modifier.size(8.dp))
            }
        }
    }
}