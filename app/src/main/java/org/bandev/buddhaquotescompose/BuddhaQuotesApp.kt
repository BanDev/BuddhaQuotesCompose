package org.bandev.buddhaquotescompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import org.bandev.buddhaquotescompose.scenes.DailyQuoteScene
import org.bandev.buddhaquotescompose.scenes.HomeScene
import org.bandev.buddhaquotescompose.scenes.InsideListScene
import org.bandev.buddhaquotescompose.scenes.ListsScene
import org.bandev.buddhaquotescompose.scenes.MeditateScene
import org.bandev.buddhaquotescompose.scenes.SettingsScene
import org.bandev.buddhaquotescompose.scenes.about.AboutScene
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.toBoolean
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme
import org.bandev.buddhaquotescompose.ui.theme.EdgeToEdgeContent

@Composable
fun BuddhaQuotesApp() {
    val settings = SettingsViewModel(LocalContext.current)

    BuddhaQuotesComposeTheme(darkTheme = settings.getThemeLive().toBoolean()) {
        EdgeToEdgeContent {
            var toolbarTitle by remember { mutableStateOf(R.string.app_name) }
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute =
                navBackStackEntry?.destination?.route ?: Scene.Home.route
            Column {
                TopAppBar(
                    title = { Text(stringResource(toolbarTitle)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (scaffoldState.drawerState.isClosed) {
                                        scaffoldState.drawerState.open()
                                    } else {
                                        scaffoldState.drawerState.close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    contentPadding = WindowInsets.statusBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                        .asPaddingValues(),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.background,
                    elevation = 0.dp
                )
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        Spacer(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .fillMaxWidth()
                        )
                    },
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
                    drawerElevation = 0.dp
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
                        composable(Scene.DailyQuote.route) {
                            toolbarTitle = R.string.daily_quote
                            DailyQuoteScene()
                        }
                        composable(Scene.Meditate.route) {
                            toolbarTitle = R.string.meditate
                            MeditateScene()
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