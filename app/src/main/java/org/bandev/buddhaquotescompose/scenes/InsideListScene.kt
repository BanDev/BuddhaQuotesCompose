package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel

@Composable
fun InsideListScene(listId: Int, viewModel: BuddhaQuotesViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}