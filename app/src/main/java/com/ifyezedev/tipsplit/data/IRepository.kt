package com.ifyezedev.tipsplit.data

import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun saveAppTheme(theme: AppTheme)
    fun getThemeFlow(): Flow<Int>
}