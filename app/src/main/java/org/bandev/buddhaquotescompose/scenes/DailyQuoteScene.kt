package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    Column {
        Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
            quote.AsCard()
        }
    }
}