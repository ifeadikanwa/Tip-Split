package com.ifyezedev.tipsplit.data

import kotlinx.coroutines.flow.Flow

interface IAppThemePreference {
    val themeFlow: Flow<Int>
    suspend fun saveAppTheme(theme: AppTheme)
}