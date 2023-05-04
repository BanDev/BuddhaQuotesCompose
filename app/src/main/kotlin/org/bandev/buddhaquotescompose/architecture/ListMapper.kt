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

package org.bandev.buddhaquotescompose.architecture

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.EmojiSymbols
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.Subject
import androidx.compose.material.icons.rounded.ThumbUp
import org.bandev.buddhaquotescompose.architecture.lists.List1
import org.bandev.buddhaquotescompose.items.ListData
import org.bandev.buddhaquotescompose.items.ListIcon
import org.bandev.buddhaquotescompose.ui.theme.bandev
import org.bandev.buddhaquotescompose.ui.theme.blueAccent
import org.bandev.buddhaquotescompose.ui.theme.favourite
import org.bandev.buddhaquotescompose.ui.theme.greenAccent
import org.bandev.buddhaquotescompose.ui.theme.lightBlueAccent
import org.bandev.buddhaquotescompose.ui.theme.orangeAccent
import org.bandev.buddhaquotescompose.ui.theme.tealAccent
import org.bandev.buddhaquotescompose.ui.theme.violetAccent
import org.bandev.buddhaquotescompose.ui.theme.yellowAccent

object ListMapper {

    val listIcons: List<ListIcon> = listOf(
        ListIcon(0, Icons.Rounded.FavoriteBorder, favourite),
        ListIcon(1, Icons.Rounded.Subject, blueAccent),
        ListIcon(2, Icons.Rounded.HistoryEdu, greenAccent),
        ListIcon(3, Icons.Rounded.History, yellowAccent),
        ListIcon(4, Icons.Rounded.Interests, tealAccent),
        ListIcon(5, Icons.Rounded.FormatQuote, violetAccent),
        ListIcon(6, Icons.Rounded.EmojiSymbols, orangeAccent),
        ListIcon(7, Icons.Rounded.ThumbUp, lightBlueAccent),
        ListIcon(8, Icons.Rounded.EmojiEvents, bandev)
    )

    suspend fun convert(list: List1, repo: Repository.ListQuotes): ListData =
        ListData(list.id, list.title, repo.count(list.id), list.system, listIcons[list.icon])
}
