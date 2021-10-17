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

package org.bandev.buddhaquotescompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Source
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.entity.Library
import compose.icons.SimpleIcons
import compose.icons.simpleicons.*
import org.bandev.buddhaquotescompose.items.LibraryIcon

object LibraryHelper {
    private const val androidDeveloperWebsite = "developer.android.com"
    private const val githubWebsite = "github.com"
    private const val googleWebsite = "google.com"
    private const val kotlinWebsite = "kotlinlang.org"
    private const val jetbrainsWebsite = "jetbrains.org"
    private const val sourceforgeWebsite = "sourceforge.net"

    fun websiteIcon(library: Library): LibraryIcon {
        val website = library.libraryWebsite
        return when {
            website.contains(androidDeveloperWebsite) -> LibraryIcon(icon = Icons.Rounded.Android, size = 24.dp)
            website.contains(githubWebsite) -> LibraryIcon(icon = SimpleIcons.Github, size = 20.dp)
            website.contains(googleWebsite) -> LibraryIcon(icon = SimpleIcons.Google, size = 20.dp)
            website.contains(kotlinWebsite) -> LibraryIcon(icon = SimpleIcons.Kotlin, size = 20.dp)
            website.contains(jetbrainsWebsite) -> LibraryIcon(icon = SimpleIcons.Jetbrains, size = 20.dp)
            website.contains(sourceforgeWebsite) -> LibraryIcon(icon = SimpleIcons.Sourceforge, size = 20.dp)
            else -> LibraryIcon(icon = Icons.Rounded.Public, size = 24.dp)
        }
    }

    fun sourceIcon(library: Library): LibraryIcon {
        val source = library.repositoryLink
        return when {
            source.contains(githubWebsite) -> LibraryIcon(icon = SimpleIcons.Github, size = 20.dp)
            source.contains(googleWebsite) -> LibraryIcon(icon = SimpleIcons.Google, size = 20.dp)
            else -> LibraryIcon(icon = Icons.Rounded.Source, size = 24.dp)
        }
    }

}