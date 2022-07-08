package com.shahad.app.my_school.di

import android.content.Context
import androidx.room.Room
import com.shahad.app.my_school.data.local.MySchoolDatabase
import com.shahad.app.my_school.data.local.MySchoolDao
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MySchoolDatabase =
        Room.databaseBuilder(
            context,
            MySchoolDatabase::class.java,
            MySchoolDatabase.DATABASE_NAME
        ).build()


    @Singleton
    @Provides
    fun provideDao(database: MySchoolDatabase): MySchoolDao =
        database.MySchoolDao()

}