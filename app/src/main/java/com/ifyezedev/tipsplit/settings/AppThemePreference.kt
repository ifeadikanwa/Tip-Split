package com.ifyezedev.tipsplit.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AppThemePreference(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_theme_preferences")

    companion object{
        val USER_THEME_KEY = intPreferencesKey("user_theme")
    }

    suspend fun saveAppTheme(theme: AppTheme){
        context.dataStore.edit {
            it[USER_THEME_KEY] = theme.mode
        }
    }

    val themeFlow: Flow<Int> = context.dataStore.data.map {
        it[USER_THEME_KEY] ?: AppTheme.SYSTEM_DEFAULT.mode
    }
}