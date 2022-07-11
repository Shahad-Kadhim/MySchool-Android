package com.shahad.app.my_school.data.remote

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.shahad.app.my_school.data.local.DataStorePreferences
import com.shahad.app.my_school.test.BuildConfig
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.ui.register.TeacherRegisterBody
import com.shahad.app.my_school.util.DataClassParser
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MySchoolServiceTest {

    var mockWebServer = MockWebServer()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val dataStorePreferences: DataStorePreferences = DataStorePreferences(context)
    private val dataClassParser = DataClassParser()
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(AuthInterceptor(dataStorePreferences))
        .addInterceptor(loggingInterceptor)
        .build()

    val api = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
        .addConverterFactory(
            GsonConverterFactory
                .create( GsonBuilder().setLenient().create() )
        )
        .build()
        .create(MySchoolService::class.java)

    @Before
    fun setUp(){
        mockWebServer.start()
    }

    @After
    fun shutdownServer(){
        mockWebServer.shutdown()
    }

    @Test
    fun should_Return200ok_When_NewUserAdded(){
        //given
        val user = TeacherRegisterBody("teacher 1","123456789", "math", 123456789, "MockFirebaseToken")
        val body = getRequestBody(user)

        //when
        runBlocking {
            val oo= api.addUser(Role.TEACHER.name,body)
            assertThat(oo.code()).isEqualTo(200)
        }


    }

    private fun getRequestBody(entity: Any) =
        dataClassParser.parseToJson(entity)
}
