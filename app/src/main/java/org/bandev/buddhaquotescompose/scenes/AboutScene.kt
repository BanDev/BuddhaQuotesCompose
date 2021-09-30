package org.bandev.buddhaquotescompose.scenes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mikepenz.aboutlibraries.Libs
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.BuildConfig
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.ui.theme.DarkBackground
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            pages.forEachIndexed { index, titleRes ->
                Tab(
                    text = { Text(stringResource(id = titleRes).uppercase()) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(count = pages.size, state = pagerState) { page ->
            if (page == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_buddha),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = stringResource(id = R.string.app_name))
                    Text(text = BuildConfig.VERSION_NAME)
                }
            } else if (page == 1) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(Libs(context).libraries) { library ->
                        val license = library.licenses?.firstOrNull()
                        var expanded by remember { mutableStateOf(false) }
                        Card(
                            onClick = { expanded = !expanded },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(10.dp),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(11.dp),
                            backgroundColor = LighterBackground
                        ) {
                            Column {
                                Column(Modifier.padding(20.dp)) {
                                    Row {
                                        Text(
                                            text = library.libraryName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Text(
                                                text = library.libraryVersion,
                                                color = Color.LightGray,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                    Text(
                                        text = library.author,
                                        color = Color.LightGray,
                                        fontSize = 14.sp
                                    )
                                    if (license != null) {
                                        Text(
                                            text = license.licenseName,
                                            modifier = Modifier.padding(top = 10.dp),
                                            color = Color.LightGray,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                                if (license != null) {
                                    AnimatedVisibility(visible = expanded) {
                                        Column(Modifier.background(DarkerBackground)) {
                                            MarkdownText(
                                                markdown = license.licenseShortDescription,
                                                modifier = Modifier.padding(20.dp),
                                                color = Color.LightGray,
                                                fontSize = 14.sp
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
}