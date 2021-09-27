package com.ifyezedev.tipsplit.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(var theme: AppTheme) : IRepository {
//    lateinit var theme: AppTheme

    override suspend fun saveAppTheme(theme: AppTheme) {
        this.theme = theme
    }

    override fun getThemeFlow(): Flow<Int> = flow {
        emit(theme.mode)
    }
}