package org.bandev.buddhaquotescompose.sheets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeker.sheets.core.utils.BaseConstants
import com.maxkeppeler.sheets.info.InfoView
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import kotlinx.coroutines.launch
import org.bandev.buddhaquotescompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditateHelpSheet(
    sheetState: SheetState,
    onClose: () -> Unit
) {
    val meditateAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.meditation))
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState
    ) {
        InfoView(
            useCaseState = rememberUseCaseState(),
            selection = InfoSelection(
                negativeButton = null,
                positiveButton = BaseConstants.DEFAULT_POSITIVE_BUTTON,
                onPositiveClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onClose()
                        }
                    }
                }
            ),
            header = Header.Custom {
                LottieAnimation(
                    composition = meditateAnimation,
                    modifier = Modifier.height(150.dp),
                    iterations = LottieConstants.IterateForever
                )
                Text(
                    text = "Meditate help",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge,
                )
                Divider()
            },
            body = InfoBody.Default(
                bodyText = "You can start a meditation session by pressing the start button below and inputting the time you want to meditate for."
            )
        )
    }
}
