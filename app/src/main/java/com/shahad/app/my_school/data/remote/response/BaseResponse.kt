package com.shahad.app.my_school.data.remote.response

data class BaseResponse<T>(
    val statusCode: Int,
    var data: T
)