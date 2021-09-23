package org.bandev.buddhaquotescompose.settings

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import org.bandev.buddhaquotescompose.R

/**
 * Create a settings store instance
 * using the [SettingsSerializer]
 * object.
 */

val Context.SettingsStoreCreator: DataStore<Settings> by dataStore(
    fileName = "Settings.pb",
    serializer = SettingsSerializer,
)

/**
 * Convert a [Settings.Theme] instance
 * into a boolean equivalent to the
 * isSystemInDarkTheme() function.
 */

@Composable
fun Settings.Theme?.boolify() = when(this) {
    Settings.Theme.LIGHT -> false
    Settings.Theme.DARK -> true
    else -> isSystemInDarkTheme()
}

/**
 * Convert a [Settings.Theme] instance
 * into a human readable string for
 * outputting to the user.
 */

@Composable
fun Settings.Theme?.stringify() = stringResource(when(this) {
    Settings.Theme.LIGHT -> R.string.light
    Settings.Theme.DARK -> R.string.dark
    else -> R.string.system
})