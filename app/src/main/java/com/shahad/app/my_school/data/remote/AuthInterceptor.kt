package com.shahad.app.my_school.data.remote

import android.content.Context
import com.shahad.app.my_school.data.local.DataStorePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorePreferences: DataStorePreferences
) :Interceptor {

    override  fun intercept(chain: Interceptor.Chain): Response {
        with(chain.request()){
            url.newBuilder()
                .build().also { httpUrl ->
                    return chain
                        .proceed(
                            addHerders(this,httpUrl)
                        )
                     //   .checkAuthentication(context)
                }
        }
    }

    private fun addHerders(request: Request, httpUrl: HttpUrl) =
        request.newBuilder()
            .url(httpUrl)
            .apply {
                runBlocking {
                    addHeader(
                        name= "Authorization",
                        value= "Bearer ${dataStorePreferences.readTokenPre().first()}"
                    )
                }
            }
            .addHeader("Content-Type","application/json")
            .build()

}

