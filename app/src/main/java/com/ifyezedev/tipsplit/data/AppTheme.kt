package com.ifyezedev.tipsplit.data

import androidx.appcompat.app.AppCompatDelegate

enum class AppTheme(val mode: Int) {
    SYSTEM_DEFAULT(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO),
    DARK(AppCompatDelegate.MODE_NIGHT_YES)
}