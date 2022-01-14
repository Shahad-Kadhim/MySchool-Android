package com.shahad.app.my_school.data.remote

import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor() :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //add query parameter for authentication
        with(chain.request()){
            url.newBuilder()
                .build().also { httpUrl ->
                    return chain.proceed(
                        this.newBuilder().url(httpUrl).addHeader("Content-Type","application/json").build()
                    )
                }
        }
    }

}

