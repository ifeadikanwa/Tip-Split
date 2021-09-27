package com.ifyezedev.tipsplit.di

import android.content.Context
import com.ifyezedev.tipsplit.data.AppThemePreference
import com.ifyezedev.tipsplit.data.IAppThemePreference
import com.ifyezedev.tipsplit.data.IRepository
import com.ifyezedev.tipsplit.data.Repository
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
    fun provideDataStore(@ApplicationContext appContext: Context) : IAppThemePreference {
        return AppThemePreference(appContext)
    }

    @Provides
    fun provideRepository(appThemePreference: IAppThemePreference) : IRepository {
        return Repository(appThemePreference)
    }
}