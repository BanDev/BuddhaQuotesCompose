package org.bandev.buddhaquotescompose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.scenes.AboutScene
import org.bandev.buddhaquotescompose.scenes.HomeScene
import org.bandev.buddhaquotescompose.scenes.ListsScene
import org.bandev.buddhaquotescompose.scenes.SettingsScene
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme
import org.bandev.buddhaquotescompose.ui.theme.DarkBackground
import org.bandev.buddhaquotescompose.ui.theme.LightBackground

@Composable
fun BuddhaQuotesApp() {
    val darkTheme = isSystemInDarkTheme()
    BuddhaQuotesComposeTheme() {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }
            var toolbarTitle by remember { mutableStateOf(R.string.app_name)}

            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Scene.Home.route
            Column {
                TopAppBar(
                    title = { Text(stringResource(toolbarTitle)) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() } }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.statusBars,
                        applyStart = true,
                        applyTop = true,
                        applyEnd = true,
                    ),
                    backgroundColor = if (darkTheme) DarkBackground else LightBackground,
                    contentColor = if (darkTheme) Color.White else Color.Black,
                    elevation = 0.dp
                )
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        AppDrawer(
                            navigateTo = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            currentScreen = currentRoute,
                            closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } }
                        )
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Scene.Home.route
                    ) {
                        composable(Scene.Home.route) {
                            toolbarTitle = R.string.app_name
                            HomeScene()
                        }
                        composable(Scene.Lists.route) {
                            toolbarTitle = R.string.your_lists
                            ListsScene(
                                navController = navController
                            )
                        }
                        composable(Scene.Settings.route) {
                            toolbarTitle = R.string.settings
                            SettingsScene()
                        }
                        composable(Scene.About.route) {
                            toolbarTitle = R.string.about
                            AboutScene()
                        }
                    }
                }
            }
        }
    }
}
