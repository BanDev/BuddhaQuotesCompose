package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote

@Composable
fun DailyQuoteScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
) {

    var quote by remember { mutableStateOf(Quote(1, R.string.blank, false)) }

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.Quotes().getDaily { quote = it }
        }
    )

    var isLiked by remember { mutableStateOf(quote.liked) }
    val context = LocalContext.current

    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        quote.AsCard()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_daily_specials),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Card(
                    modifier = Modifier.padding(20.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = 4.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
                            isChecked = isLiked,
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
                    }
                }
            }
        }
    }
}