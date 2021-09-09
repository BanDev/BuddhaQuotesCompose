package org.bandev.buddhaquotescompose.settings

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class SettingsRepository(private val store: DataStore<Settings>) {

    val settings = MutableStateFlow(store.data)

    suspend fun getTheme() : Settings.Theme {
        return try {
            store.data.first().theme
        } catch (e : Exception) {
            e.printStackTrace()
            Settings.getDefaultInstance().theme
        }
    }

    suspend fun setTheme(theme: Settings.Theme) {
        store.updateData { current ->
            current.toBuilder().setTheme(theme).build()
        }
    }
}

