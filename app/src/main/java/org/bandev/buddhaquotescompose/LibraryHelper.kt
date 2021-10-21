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
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.TextSnippet
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Freebsd
import compose.icons.simpleicons.Github
import compose.icons.simpleicons.Jetbrains
import compose.icons.simpleicons.Sourceforge
import org.bandev.buddhaquotescompose.items.LibraryIconPainter
import org.bandev.buddhaquotescompose.items.LibraryIconVector

object LibraryHelper {
    private const val androidDeveloperWebsite = "developer.android.com"
    private const val githubWebsite = "github.com"
    private const val googleWebsite = "google.com"
    private const val kotlinWebsite = "kotlinlang.org"
    private const val jetbrainsWebsite = "jetbrains.org"
    private const val sourceforgeWebsite = "sourceforge.net"

    @Composable
    fun licenseIcon(license: License): Any {
        return when (license.licenseName) {
            stringResource(id = R.string.license_Apache_2_0_licenseName) -> LibraryIconPainter(drawable = R.drawable.ic_apache, size = 24.dp)
            stringResource(id = R.string.license_BSD_2_Clause_licenseName) -> LibraryIconVector(imageVector = SimpleIcons.Freebsd, size = 20.dp, tint = Color(0xFFAB2B28))
            stringResource(id = R.string.license_BSD_3_Clause_licenseName) -> LibraryIconVector(imageVector = SimpleIcons.Freebsd, size = 20.dp, tint = Color(0xFFAB2B28))
            else -> LibraryIconVector(imageVector = Icons.Rounded.TextSnippet)
        }
    }

    fun websiteIcon(library: Library): Any {
        library.libraryWebsite.run {
            return when {
                contains(androidDeveloperWebsite) -> LibraryIconVector(imageVector = Icons.Rounded.Android, tint = Color(0xFF3DDC84))
                contains(githubWebsite) -> LibraryIconVector(imageVector = SimpleIcons.Github, size = 20.dp)
                contains(googleWebsite) -> LibraryIconPainter(drawable = R.drawable.ic_google_logo)
                contains(kotlinWebsite) -> LibraryIconPainter(drawable = R.drawable.ic_kotlin)
                contains(jetbrainsWebsite) -> LibraryIconVector(imageVector = SimpleIcons.Jetbrains, size = 20.dp)
                contains(sourceforgeWebsite) -> LibraryIconVector(imageVector = SimpleIcons.Sourceforge, tint = Color(0xFFFF6600))
                else -> LibraryIconVector(imageVector = Icons.Rounded.Language)
            }
        }
    }

    fun sourceIcon(library: Library): Any {
        library.repositoryLink.run {
            return when {
                contains(githubWebsite) -> LibraryIconVector(imageVector = SimpleIcons.Github, size = 20.dp)
                contains(googleWebsite) -> LibraryIconPainter(drawable = R.drawable.ic_google_logo)
                else -> LibraryIconVector(imageVector = Icons.Rounded.Code, size = 24.dp)
            }
        }
    }

    fun linkFixer(website: String): String {
        var fixedWebsite = website
        if (fixedWebsite.endsWith("/")) fixedWebsite = fixedWebsite.dropLast(1)
        if (fixedWebsite.endsWith("/tree/main")) fixedWebsite = fixedWebsite.replace("/tree/main", "")
        if (fixedWebsite.endsWith(".git")) fixedWebsite = fixedWebsite.replace(".git", "")
        if (fixedWebsite.startsWith("git://")) fixedWebsite = fixedWebsite.replace("git://", "https://")
        return fixedWebsite
    }

}