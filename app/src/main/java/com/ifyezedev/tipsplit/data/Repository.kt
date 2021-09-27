package com.ifyezedev.tipsplit.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appThemePreference: IAppThemePreference) : IRepository {

    override suspend fun saveAppTheme(theme: AppTheme) = appThemePreference.saveAppTheme(theme)

    override fun getThemeFlow() : Flow<Int> = appThemePreference.themeFlow

}