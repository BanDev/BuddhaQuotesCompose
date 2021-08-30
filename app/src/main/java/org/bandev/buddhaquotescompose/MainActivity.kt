package org.bandev.buddhaquotescompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import org.bandev.buddhaquotescompose.ui.theme.BuddhaQuotesComposeTheme
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedTransitionTargetStateParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuddhaQuotesComposeTheme {
                ProvideWindowInsets {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(stringResource(R.string.app_name)) },
                                navigationIcon = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Menu,
                                            contentDescription = null
                                        )
                                    }
                                },
                                contentPadding = rememberInsetsPaddingValues(
                                    LocalWindowInsets.current.statusBars,
                                    applyBottom = false,
                                ),
                                backgroundColor = Color.Transparent,
                                elevation = 0.dp
                            )
                        },
                        bottomBar = {
                            Spacer(
                                Modifier
                                    .navigationBarsHeight()
                                    .fillMaxWidth())
                        },
                    ) { contentPadding ->
                        Box(Modifier.padding(contentPadding)) {
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
                                                modifier = Modifier.padding(15.dp)
                                            )
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                IconButton(
                                                    onClick = { /*TODO*/ },
                                                    modifier = Modifier.padding(15.dp)
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
                }
            }
        }
    }
}