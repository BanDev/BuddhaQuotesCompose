package org.bandev.buddhaquotescompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

@Composable
fun HomeScene(openDrawer: () -> Unit) {
        Column {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { openDrawer() }) {
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
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
            Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(11.dp),
                    backgroundColor = LighterBackground
                ) {
                    Column(Modifier.padding()) {
                        Text(
                            text = "A good friend who points out mistakes and imperfections and rebukes evil is to be respected as if he reveals a secret of hidden treasure",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(20.dp)
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(DarkerBackground)
                        ) {
                            var isLiked by remember { mutableStateOf(false) }
                            FavoriteButton(
                                isChecked = isLiked,
                                onClick = { isLiked = !isLiked },
                                modifier = Modifier.padding(10.dp)
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.MoreHoriz,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_buddha),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }