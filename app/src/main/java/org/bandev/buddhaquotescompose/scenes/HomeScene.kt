package org.bandev.buddhaquotescompose.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import org.bandev.buddhaquotescompose.FavoriteButton
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotescompose.items.Quote
import org.bandev.buddhaquotescompose.ui.theme.Shapes
import org.bandev.buddhaquotescompose.ui.theme.Typography
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
                            viewModel.Quotes().setLike(quote.id, isLiked)
                        }
                    }
                )
            }
    )
    Column(Modifier.fillMaxWidth()) {
        Spacer(Modifier.statusBarsPadding())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold
            )
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_anahata),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.1f
        )
        Crossfade(targetState = quote) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(it.resource),
                    fontSize = 26.sp,
                    lineHeight = 35.sp,
                    style = Typography.body1
                )
                Spacer(Modifier.size(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = stringResource(R.string.attribution_buddha),
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        style = Typography.caption
                    )
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