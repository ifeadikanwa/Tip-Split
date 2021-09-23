package com.ifyezedev.tipsplit.data

import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.data.IAppThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppThemePreference(var appTheme: IAppTheme) : IAppThemePreference {
    override suspend fun saveAppTheme(theme: IAppTheme) {
        appTheme = theme
    }

    override val themeFlow: Flow<Int> = flow {
        emit(appTheme.mode)
    }

    fun themeFlowFunction(): Flow<Int> = flow {
        emit(appTheme.mode)
    }


}