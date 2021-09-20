package com.ifyezedev.tipsplit

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ifyezedev.tipsplit.settings.AppTheme
import com.ifyezedev.tipsplit.settings.AppThemePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appThemePreference: AppThemePreference) {

    suspend fun saveAppTheme(theme: AppTheme) = appThemePreference.saveAppTheme(theme)

    fun getTheme() : LiveData<Int> = appThemePreference.themeFlow.asLiveData()

}