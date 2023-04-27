package org.bandev.buddhaquotescompose.scenes.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.BuildConfig
import org.bandev.buddhaquotescompose.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .wrapContentSize(Alignment.BottomCenter)
                        .width(120.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp)),
                    color = MaterialTheme.colors.primary,
                )
            }
        ) {
            pages.forEachIndexed { index, titleRes ->
                Tab(
                    text = {
                        Text(
                            stringResource(id = titleRes),
                            style = MaterialTheme.typography.subtitle1,
                        )
                    },
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
                LibrariesContainer(Modifier.fillMaxSize())
            }
        }
    }
}
