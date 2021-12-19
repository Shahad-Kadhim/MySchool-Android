package com.shahad.app.my_school.di

import com.shahad.app.my_school.data.remote.AuthInterceptor
import com.shahad.app.my_school.data.remote.MySchoolService
import com.shahad.app.my_school.util.Constants
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMySchoolService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): MySchoolService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL) // change base url later
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(MySchoolService::class.java)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory =
        GsonConverterFactory.create()

}