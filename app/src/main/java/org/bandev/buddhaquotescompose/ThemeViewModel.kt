package org.bandev.buddhaquotescompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    private val _theme = MutableLiveData(2)
    val theme: LiveData<Int> = _theme

    fun onThemeChanged(newTheme: String) {
        when (newTheme) {
            "Auto" -> _theme.value = 0
            "Light" -> _theme.value = 1
            "Dark" -> _theme.value = 2
        }
    }
}