package com.ifyezedev.tipsplit.di

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.data.AppThemePreference
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltAndroidApp
class TipSplitApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val pref = AppThemePreference(this)
        val mode = pref.themeFlow.asLiveData().value ?: AppTheme.SYSTEM_DEFAULT.mode
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}