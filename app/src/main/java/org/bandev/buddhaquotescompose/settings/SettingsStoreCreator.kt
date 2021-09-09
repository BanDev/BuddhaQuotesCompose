package org.bandev.buddhaquotescompose.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.SettingsStoreCreator: DataStore<Settings> by dataStore(
    fileName = "Settings.pb",
    serializer = SettingsSerializer,
)