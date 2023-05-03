package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel

@Composable
fun InsideListScene(listId: Int, viewModel: BuddhaQuotesViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit, block = { text = viewModel.Lists().get(listId).title })
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
    }
}