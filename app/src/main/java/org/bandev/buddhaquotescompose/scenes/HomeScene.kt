package org.bandev.buddhaquotescompose.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

@Composable
fun HomeScene(
    openDrawer: () -> Unit,
    viewModel: BuddhaQuotesViewModel
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
                        text = stringResource(quote.resource),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            viewModel.Quotes().getRandom { quote = it }
                        },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = null
                        )
                    }
                    FavoriteButton(
                        isChecked = isLiked,
                        onClick = {
                            isLiked = !isLiked
                            viewModel.Quotes().setLike(quote.id, isLiked)
                        },
                        modifier = Modifier.padding(5.dp)
                    )
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
                                  viewModel.Quotes().getRandom { quote = it }
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