package com.shahad.app.my_school.di

import com.shahad.app.my_school.data.MySchoolFakeRepository
import com.shahad.app.my_school.data.MySchoolRepositoryImpl
import com.shahad.app.my_school.data.MySchoolRepository
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMySchoolRepository(
        impl: MySchoolFakeRepository
    ): MySchoolRepository

}