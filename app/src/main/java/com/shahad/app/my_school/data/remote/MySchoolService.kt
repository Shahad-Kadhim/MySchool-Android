package com.shahad.app.my_school.data.remote

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MySchoolService{

    @POST("/teacher/new")
    suspend fun addTeacher(@Body registerBody: JsonElement): Response<String>

    @POST("/teacher/login")
    suspend fun loginTeacher(@Body loginBody: JsonElement):  Response<String>

}