package org.bandev.buddhaquotescompose.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.architecture.QuoteMapper
import org.bandev.buddhaquotescompose.items.AnimatedHeart
import org.bandev.buddhaquotescompose.items.Heart
import org.bandev.buddhaquotescompose.items.Quote
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScene(viewModel: BuddhaQuotesViewModel = viewModel()) {
    val quote by viewModel.selectedQuote.collectAsStateWithLifecycle(Quote())
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val hearts = remember { mutableStateListOf<Heart>() }

    Scaffold(
        bottomBar = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                listOf("Quotes", "Lists", "Meditation").forEachIndexed { index, title ->
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
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = { offset ->
                                        if (!quote.isLiked) {
                                            scope.launch {
                                                viewModel.toggleLikedOnSelectedQuote()
                                                viewModel.Quotes().setLike(quote.id, !quote.isLiked)
                                            }
                                        }
                                        hearts += Heart(
                                            position = offset,
                                            rotation = Random.nextFloat() * 40 - 20,
                                            size = Animatable(0f),
                                            alpha = Animatable(1f)
                                        )
                                    }
                                )
                            }
                    ) {
                        hearts.forEachIndexed { index, heart ->
                            AnimatedHeart(heart) {
                                hearts.removeAt(index)
                            }
                        }
                    }
                    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(20.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_left_quote),
                                    contentDescription = null
                                )
                                AnimatedContent(targetState = quote.resource) {
                                    Text(text = stringResource(it))
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
                                            scope.launch {
                                                viewModel.setNewQuote(viewModel.Quotes().get(id))
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
                                        checked = quote.isLiked,
                                        onClick = {
                                            scope.launch {
                                                viewModel.toggleLikedOnSelectedQuote()
                                                viewModel.Quotes().setLike(quote.id, !quote.isLiked)
                                            }
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
                                            scope.launch {
                                                viewModel.setNewQuote(viewModel.Quotes().get(id))
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
