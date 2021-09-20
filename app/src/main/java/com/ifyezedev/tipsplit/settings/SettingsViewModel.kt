package com.ifyezedev.tipsplit.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val TAG = "Settings ViewModel"
    val mode = repository.getTheme()

    fun changeAppTheme(theme: AppTheme) {
        viewModelScope.launch {
            repository.saveAppTheme(theme)
        }
    }




}