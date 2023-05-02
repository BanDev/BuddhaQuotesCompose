package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.Scene
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.ListData

@Composable
fun ListsScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
    navController: NavController
) {
    var lists: List<ListData> by remember { mutableStateOf(listOf()) }
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.Lists().getAll { lists = it }
        }
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.Lists().new("test") },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(lists) { list ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 7.dp)
                        .clickable { navController.navigate("${Scene.InsideList.route}/${list.id}") },
                    shape = RoundedCornerShape(11.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row {
                        Box(
                            Modifier.fillMaxHeight().width(60.dp).background(list.icon.colour),
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
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = if (list.count == 1) R.string.quote_count else R.string.quotes_count, list.count),
                                    modifier = Modifier.padding(20.dp),
                                    fontSize = 16.sp
                                )
                                Box(
                                    Modifier.fillMaxWidth(),
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
