package org.bandev.buddhaquotescompose.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.architecture.QuoteMapper
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.ui.theme.heart

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScene(viewModel: BuddhaQuotesViewModel = viewModel()) {
    var quote by remember { mutableStateOf(Quote(1, R.string.blank, false)) }
    LaunchedEffect(
        key1 = Unit,
        block = { viewModel.Quotes().getRandom { quote = it } }
    )
    var isLiked by remember { mutableStateOf(quote.liked) }
    val context = LocalContext.current
    var isAnimating by remember { mutableStateOf(false) }
    val heartSize by animateDpAsState(
        targetValue = if (isAnimating) 100.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 500f
        )
    )
    var heartPosition by remember { mutableStateOf(Offset.Zero) }
    val pages = remember { listOf("Quotes", "Lists", "Meditation") }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            pageCount = 3,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = { offset ->
                                        isAnimating = true
                                        heartPosition = offset
                                        if (!isLiked) {
                                            isLiked = !isLiked
                                            viewModel.Quotes().setLike(quote.id, isLiked)
                                        }
                                    }
                                )
                            }
                    )
                    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(20.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_left_quote),
                                    contentDescription = null
                                )
                                AnimatedContent(targetState = quote) {
                                    Text(text = stringResource(it.resource))
                                }
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_right_quote),
                                        contentDescription = null,
                                    )
                                }
                                Text(text = stringResource(R.string.attribution_buddha))
                            }
                        }
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.image_anahata),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                            )
                            ElevatedCard(Modifier.padding(20.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                                ) {
                                    IconButton(
                                        onClick = {
                                            val id = if (quote.id > QuoteMapper.quotes.size) {
                                                QuoteMapper.quotes.keys.first()
                                            } else if (quote.id < 1) {
                                                QuoteMapper.quotes.size
                                            } else {
                                                quote.id - 1
                                            }
                                            viewModel.Quotes().get(id) {
                                                quote = it
                                                isLiked = quote.liked
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ChevronLeft,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        onClick = { context.shareQuote(quote = quote) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Share,
                                            contentDescription = null
                                        )
                                    }
                                    FavoriteButton(
                                        checked = isLiked,
                                        onClick = {
                                            isLiked = !isLiked
                                            viewModel.Quotes().setLike(quote.id, isLiked)
                                        }
                                    )
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Rounded.AddCircleOutline,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            val id = if (quote.id > QuoteMapper.quotes.size) {
                                                QuoteMapper.quotes.keys.first()
                                            } else if (quote.id < 1) {
                                                QuoteMapper.quotes.size
                                            } else {
                                                quote.id + 1
                                            }
                                            viewModel.Quotes().get(id) {
                                                quote = it
                                                isLiked = quote.liked
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ChevronRight,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Box(Modifier.fillMaxSize()) {
                        if (isAnimating) {
                            Icon(
                                imageVector = Icons.Rounded.Favorite,
                                tint = heart,
                                modifier = Modifier
                                    .size(heartSize)
                                    .offset { IntOffset(heartPosition.x.toInt() - 100, heartPosition.y.toInt() - 100) },
                                contentDescription = null
                            )
                            if (heartSize == 100.dp) {
                                isAnimating = false
                            }
                        }
                    }
                }
                1 -> Column(Modifier.fillMaxSize()) {
                    Text(text = "Page $page")
                }
                else -> MeditateScene()
            }
        }
    }
}

fun Context.shareQuote(quote: Quote) {
    Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            """
                ${getString(quote.resource)}
                
                ${getString(R.string.attribution_buddha)}
            """.trimIndent()
        )
        type = "text/plain"
    }.let { startActivity(Intent.createChooser(it, null)) }
}