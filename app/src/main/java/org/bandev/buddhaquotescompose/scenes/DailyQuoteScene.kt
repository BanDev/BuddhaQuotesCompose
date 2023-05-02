package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.Date
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.ui.theme.Shapes

@Composable
fun DailyQuoteScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
) {
    var quote by remember { mutableStateOf(Quote(1, R.string.blank, false)) }

    LaunchedEffect(
        key1 = Unit,
        block = { viewModel.Quotes().getDaily { quote = it } }
    )

    var isLiked by remember { mutableStateOf(quote.liked) }
    val context = LocalContext.current

    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.medium
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_left_quote),
                        contentDescription = null,
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = Date.getWeekdayWithOrdinal())
                        Text(text = Date.getMonthAndYear())
                    }
                }
                Text(text = stringResource(quote.resource))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
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
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
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
                }
            }
        }
    }
}