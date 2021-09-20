package com.ifyezedev.tipsplit.di

import android.content.Context
import com.ifyezedev.tipsplit.data.AppThemePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context) : AppThemePreference {
        return AppThemePreference(appContext)
    }
}