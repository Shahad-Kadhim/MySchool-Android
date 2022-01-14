package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import retrofit2.Response

interface MySchoolRepository{

    suspend fun addTeacher(registerBody: JsonElement): Response<String>

    suspend fun loginTeacher(loginBody: JsonElement): Response<String>

}