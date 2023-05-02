package org.bandev.buddhaquotescompose.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.ui.theme.Shapes
import org.bandev.buddhaquotescompose.ui.theme.heart

@Composable
fun HomeScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
) {
    var quote by remember { mutableStateOf(Quote(1, R.string.blank, false)) }
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.Quotes().getRandom { quote = it }
        }
    )
    var isLiked by remember { mutableStateOf(quote.liked) }
    val context = LocalContext.current
    var isAnimating by remember { mutableStateOf(false) }
    val animatedSize by animateDpAsState(
        targetValue = if (isAnimating) 100.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 500f
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        isAnimating = true
                        if (!isLiked) {
                            isLiked = !isLiked
                            viewModel
                                .Quotes()
                                .setLike(quote.id, isLiked)
                        }
                    }
                )
            }
    )
    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        Crossfade(targetState = quote, label = "fade") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = Shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_left_quote),
                        contentDescription = null,
                    )
                    Text(text = stringResource(it.resource))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
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
            Card(
                modifier = Modifier.padding(20.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            viewModel.Quotes().getRandom {
                                quote = it
                                isLiked = quote.liked
                            }
                        },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            context.shareQuote(quote = quote)
                        },
                        modifier = Modifier.padding(5.dp)
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
                        },
                        modifier = Modifier.padding(5.dp)
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddCircleOutline,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.Quotes().getRandom {
                                quote = it
                                isLiked = quote.liked
                            }
                        },
                        modifier = Modifier.padding(5.dp)
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
                    .size(animatedSize)
                    .align(Alignment.Center),
                contentDescription = null
            )
            if (animatedSize == 100.dp) {
                isAnimating = false
            }
        }
    }
}

fun Context.shareQuote(quote: Quote) {
    Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(quote.resource) + "\n\n" + getString(R.string.attribution_buddha)
        )
        type = "text/plain"
    }.run { startActivity(Intent.createChooser(this, null)) }
}