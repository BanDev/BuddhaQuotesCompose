package org.bandev.buddhaquotescompose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme

@Composable
fun BuddhaQuotesApp(
    viewModel: BuddhaQuotesViewModel
) {
    BuddhaQuotesComposeTheme() {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }

            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Scene.Home.route
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
                        HomeScene(
                            openDrawer = {
                                coroutineScope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() }
                            },
                            viewModel = viewModel
                        )
                    }
                    composable(Scene.Settings.route) {
                        SettingsScene(
                            openDrawer = {
                                coroutineScope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() }
                            }
                        )
                    }
                    composable(Scene.About.route) {
                        AboutScene(
                            openDrawer = {
                                coroutineScope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() }
                            }
                        )
                    }
                }
            }
        }
    }
}
