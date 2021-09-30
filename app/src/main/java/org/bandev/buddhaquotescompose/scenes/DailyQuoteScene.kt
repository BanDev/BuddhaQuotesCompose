package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Today
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.Date
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = MaterialTheme.shapes.small,
            elevation = 4.dp
        ) {
            Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Image(
                        imageVector = Icons.Rounded.Today,
                        contentDescription = null, // decorative
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = Date.getAsString("dd/MM"),
                        style = MaterialTheme.typography.body2,
                    )
            }
        }
        quote.AsCard()
    }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
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