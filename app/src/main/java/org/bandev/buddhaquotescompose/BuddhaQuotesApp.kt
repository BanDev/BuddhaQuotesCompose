package org.bandev.buddhaquotescompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.scenes.*
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.boolify
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme

@Composable
fun BuddhaQuotesApp() {

    val settings = SettingsViewModel(LocalContext.current)

    BuddhaQuotesComposeTheme(darkTheme = settings.getThemeLive().boolify()) { colors ->
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
                    backgroundColor = colors.background,
                    contentColor = colors.onBackground,
                    elevation = 0.dp
                )
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = { Spacer(modifier = Modifier
                        .navigationBarsHeight()
                        .fillMaxWidth()) },
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
                    },
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Scene.Home.route,
                        modifier = Modifier.padding(paddingValues = paddingValues)
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
                        composable(Scene.InsideList.route) {
                            toolbarTitle = R.string.app_name
                            InsideListScene()
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
