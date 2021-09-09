package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.Scene
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.List
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListsScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
    navController: NavController
) {
    var lists by remember { mutableStateOf(mutableListOf(List())) }
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.Lists().getAll { lists = it }
        }
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.Lists().new("test") { }
                          },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 15.dp, top = 1.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(lists) { list ->
                Card(
                    onClick = { navController.navigate(Scene.InsideList.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 7.dp, bottom = 7.dp),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(11.dp),
                    backgroundColor = LighterBackground
                ) {
                    Row {
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .width(60.dp)
                                .background(list.icon.colour),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                imageVector = list.icon.imageVector,
                                contentDescription = null
                            )
                        }
                        Column {
                            Text(
                                text = if (list.id != 0) list.title else stringResource(id = R.string.favourites),
                                modifier = Modifier.padding(20.dp),
                                fontSize = 20.sp
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(DarkerBackground),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = if (list.count == 1) R.string.quote_count else R.string.quotes_count, list.count),
                                    modifier = Modifier.padding(20.dp),
                                    fontSize = 16.sp
                                )
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.MoreVert,
                                            contentDescription = null
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
}