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
import androidx.compose.material.icons.rounded.ElectricRickshaw
import androidx.compose.material.icons.rounded.EuroSymbol
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.HolidayVillage
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.ui.graphics.Color
import org.bandev.buddhaquotescompose.items.ListData
import org.bandev.buddhaquotescompose.items.ListIcon

object ListMapper {

    private val listIcons: List<ListIcon> = listOf(
        ListIcon(0, Icons.Rounded.FavoriteBorder, Color.Red),
        ListIcon(1, Icons.Rounded.List, Color.Red),
        ListIcon(2, Icons.Rounded.HistoryEdu, Color.Red),
        ListIcon(3, Icons.Rounded.History, Color.Red),
        ListIcon(4, Icons.Rounded.HolidayVillage, Color.Red),
        ListIcon(5, Icons.Rounded.FormatQuote, Color.Red),
        ListIcon(6, Icons.Rounded.EuroSymbol, Color.Red),
        ListIcon(7, Icons.Rounded.ThumbUp, Color.Red),
        ListIcon(8, Icons.Rounded.ElectricRickshaw, Color.Red)
    )

    suspend fun convert(list: Db.List1, repo: Repository.ListQuotes): ListData =
        ListData(list.id, list.title, repo.count(list.id), list.system, listIcons[list.icon])
}
