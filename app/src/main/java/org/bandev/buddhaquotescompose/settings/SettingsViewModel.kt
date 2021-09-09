package org.bandev.buddhaquotescompose.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(context: Context) : ViewModel() {

    val repository = SettingsRepository(context.SettingsStoreCreator)

    val settings = repository.settings

    fun getTheme(after: (Settings.Theme) -> Unit) {
        viewModelScope.launch {
            after(repository.getTheme())
        }
    }

    fun setTheme(theme: Settings.Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }

}