package org.bandev.buddhaquotescompose.scenes.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mikepenz.aboutlibraries.Libs
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.BuildConfig
import org.bandev.buddhaquotescompose.R

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .wrapContentSize(Alignment.BottomCenter)
                        .width(120.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp)),
                    color = MaterialTheme.colors.primary,
                )
            }
        ) {
            pages.forEachIndexed { index, titleRes ->
                Tab(
                    text = {
                        Text(
                            stringResource(id = titleRes),
                            style = MaterialTheme.typography.subtitle1,
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(count = pages.size, state = pagerState) { page ->
            if (page == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_buddha),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = stringResource(id = R.string.app_name))
                    Text(text = BuildConfig.VERSION_NAME)
                }
            } else if (page == 1) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(Libs(context).libraries) { library ->
                        val license = library.licenses?.firstOrNull()
                        val repoLink = LibraryHelper.linkFixer(library.repositoryLink)
                        val libraryWebsite = LibraryHelper.linkFixer(library.libraryWebsite)
                        Column(Modifier.padding(20.dp)) {
                            Row {
                                if (library.libraryName.isNotEmpty()) {
                                    Text(
                                        text = library.libraryName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                if (library.libraryVersion.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Text(
                                            text = library.libraryVersion,
                                            color = Color.LightGray,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                            if (library.author.isNotEmpty()) {
                                Text(
                                    text = library.author,
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                            if (license != null) {
                                Text(
                                    text = license.licenseName,
                                    modifier = Modifier.padding(top = 10.dp),
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                            if (library.libraryDescription.isNotEmpty()) {
                                Text(
                                    text = library.libraryDescription,
                                    modifier = Modifier.padding(top = 10.dp),
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            if (license != null) {
                                LibraryButton(
                                    modifier = Modifier.weight(0.5f),
                                    text = stringResource(id = R.string.license),
                                    onClick = { openDialog = true },
                                    libraryIcon = LibraryHelper.licenseIcon(license)
                                )
                            }
                            if (library.libraryWebsite.isNotEmpty() && libraryWebsite != repoLink) {
                                LibraryButton(
                                    modifier = Modifier.weight(0.5f),
                                    text = stringResource(id = R.string.website),
                                    onClick = { linkToWebpage(context, libraryWebsite) },
                                    libraryIcon = LibraryHelper.websiteIcon(library)
                                )
                            }
                            if (library.repositoryLink.isNotEmpty()) {
                                LibraryButton(
                                    modifier = Modifier.weight(0.5f),
                                    text = stringResource(id = R.string.source),
                                    onClick = { linkToWebpage(context, repoLink) },
                                    libraryIcon = LibraryHelper.sourceIcon(library)
                                )
                            }
                        }
                        Divider()
                        if (openDialog) {
                            Dialog(onDismissRequest = { openDialog = false }) {
                                Box(
                                    Modifier
                                        .wrapContentHeight()
                                        .width(width = 500.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            color = MaterialTheme.colors.background,
                                            shape = MaterialTheme.shapes.large
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (license != null) {
                                        MarkdownText(
                                            markdown = license.licenseShortDescription,
                                            modifier = Modifier.padding(20.dp),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun linkToWebpage(context: Context, URI: String) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(URI)
    startActivity(context, openURL, null)
}

@Composable
private fun LibraryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    elevation: ButtonElevation =ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
    spacerDp: Dp = 5.dp,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    libraryIcon: Any
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        elevation = elevation,
        shape = shape,
        colors = colors
    ) {
        if (libraryIcon is LibraryIconPainter) {
            Image(
                painter = painterResource(id = libraryIcon.drawable),
                contentDescription = null,
                modifier = Modifier.size(libraryIcon.size)
            )
        } else if (libraryIcon is LibraryIconVector) {
            Icon(
                imageVector = libraryIcon.imageVector,
                contentDescription = null,
                modifier = Modifier.size(libraryIcon.size),
                tint = libraryIcon.tint ?: LocalContentColor.current
            )
        }
        Spacer(Modifier.width(spacerDp))
        Text(text = text)
    }
}