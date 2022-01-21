package com.shahad.app.my_school.di

import android.content.Context
import com.shahad.app.my_school.DataStorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {

    @Provides
    @Singleton
    fun dataStorePreferences(@ApplicationContext context: Context): DataStorePreferences =
        DataStorePreferences(context = context)

}