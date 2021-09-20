package com.ifyezedev.tipsplit.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appThemePreference: AppThemePreference) {

    suspend fun saveAppTheme(theme: AppTheme) = appThemePreference.saveAppTheme(theme)

    fun getTheme() : LiveData<Int> = appThemePreference.themeFlow.asLiveData()

}