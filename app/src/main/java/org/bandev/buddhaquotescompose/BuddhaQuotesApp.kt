package org.bandev.buddhaquotescompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.scenes.AboutScene
import org.bandev.buddhaquotescompose.scenes.DailyQuoteScene
import org.bandev.buddhaquotescompose.scenes.HomeScene
import org.bandev.buddhaquotescompose.scenes.InsideListScene
import org.bandev.buddhaquotescompose.scenes.ListsScene
import org.bandev.buddhaquotescompose.scenes.MeditateScene
import org.bandev.buddhaquotescompose.scenes.SettingsScene
import org.bandev.buddhaquotescompose.settings.SettingsViewModel
import org.bandev.buddhaquotescompose.settings.toBoolean
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme
import org.bandev.buddhaquotescompose.ui.theme.EdgeToEdgeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddhaQuotesApp() {
    val settings = SettingsViewModel(LocalContext.current)
    val darkTheme = settings.getThemeLive().toBoolean()

    BuddhaQuotesComposeTheme(darkTheme = darkTheme) {
        EdgeToEdgeContent(darkTheme = darkTheme) {
            var toolbarTitle by remember { mutableStateOf("") }
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Scene.Home.route
            var openBottomSheet by rememberSaveable { mutableStateOf(false) }
            val bottomSheetState = rememberModalBottomSheetState()

            Column {
                ModalNavigationDrawer(
                    drawerContent = {
                        AppDrawer(
                            navigateTo = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            currentScreen = currentRoute,
                            closeDrawer = { scope.launch { drawerState.close() } }
                        )
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(toolbarTitle) },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch { 
                                                drawerState.apply { 
                                                    if (isClosed) open() else close() 
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
                                actions = {
                                    IconButton(
                                        onClick = { openBottomSheet = !openBottomSheet }
                                    ) {
                                        Icon(Icons.Rounded.HelpOutline, contentDescription = null)
                                    }
                                },
                                windowInsets = WindowInsets.statusBars
                                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                            )
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Scene.Home.route,
                            modifier = Modifier.padding(paddingValues = paddingValues)
                        ) {
                            composable(Scene.Home.route) {
                                toolbarTitle = stringResource(R.string.app_name)
                                HomeScene()
                            }
                            composable(Scene.Lists.route) {
                                toolbarTitle = stringResource(R.string.your_lists)
                                ListsScene(navController = navController)
                            }
                            composable(
                                route = "${Scene.InsideList.route}/{listId}",
                                arguments = listOf(navArgument("listId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val listId = backStackEntry.arguments?.getInt("listId")!!
                                val viewModel = viewModel<BuddhaQuotesViewModel>()
                                LaunchedEffect(Unit) {
                                    toolbarTitle = if (listId == 0) {
                                        "Favourite"
                                    } else {
                                        viewModel.Lists().get(listId).title
                                    }
                                }
                                InsideListScene(listId)
                            }
                            composable(Scene.DailyQuote.route) {
                                toolbarTitle = stringResource(R.string.daily_quote)
                                DailyQuoteScene()
                            }
                            composable(Scene.Meditate.route) {
                                toolbarTitle = stringResource(R.string.meditate)
                                MeditateScene()
                            }
                            composable(Scene.Settings.route) {
                                toolbarTitle = stringResource(R.string.settings)
                                SettingsScene()
                            }
                            composable(Scene.About.route) {
                                toolbarTitle =  stringResource(R.string.about)
                                AboutScene()
                            }
                        }
                        if (openBottomSheet) {
                            ModalBottomSheet(
                                onDismissRequest = { openBottomSheet = false },
                                sheetState = bottomSheetState
                            ) {
                                Text(
                                    text = "Quote help",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                                Divider()
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ){
                                    Text(
                                        text = "You can press the next button or swipe down from the top to get a new quote",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "You can change the image at the bottom by holding down on it which will bring up a selection of 16 image options. 🤩",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}