/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotescompose.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotescompose.R
import org.bandev.buddhaquotescompose.ui.theme.Shapes
import org.bandev.buddhaquotescompose.ui.theme.Typography

/**
 * An individual Quote
 */

data class Quote(
    val id: Int,
    val resource: Int,
    var liked: Boolean
) {

    @Composable
    fun AsCard() {
        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = Shapes.medium,
            elevation = 4.dp,
        ) {
            Column(Modifier.padding(20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_left_quote),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(resource),
                    style = Typography.body1
                )
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
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
    }
}