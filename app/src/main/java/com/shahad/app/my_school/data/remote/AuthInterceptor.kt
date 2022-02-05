package com.shahad.app.my_school.data.remote

import com.shahad.app.my_school.data.local.DataStorePreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
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

