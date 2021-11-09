package org.bandev.buddhaquotescompose.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.Scene
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.List
import org.bandev.buddhaquotescompose.ui.theme.DarkBackground
import org.bandev.buddhaquotescompose.ui.theme.DarkerBackground
import org.bandev.buddhaquotescompose.ui.theme.LighterBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListsScene(
    viewModel: BuddhaQuotesViewModel = viewModel(),
    navController: NavController
) {
    var lists by remember { mutableStateOf(mutableListOf(List())) }
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.Lists().getAll { lists = it }
        }
    )
    Surface(Modifier.fillMaxSize().background(DarkBackground)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(MaterialTheme.colors.background)
            ) {

            }
        }
    }
}