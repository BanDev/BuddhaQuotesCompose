package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.ui.theme.Shapes
import org.bandev.buddhaquotescompose.ui.theme.Typography

@Composable
fun FavouritesScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 15.dp, top = 1.dp, end = 15.dp)
    ) {
        viewModel.ListQuotes().getFrom(0) {
            items(it) { quote ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = Shapes.medium,
                    elevation = 4.dp,
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_left_quote),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(quote.resource),
                            style = Typography.body1
                        )
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
                        Text(
                            text = stringResource(R.string.attribution_buddha),
                            style = Typography.caption
                        )
                    }
                }
                Spacer(Modifier.size(15.dp))
            }
        }
    }
}