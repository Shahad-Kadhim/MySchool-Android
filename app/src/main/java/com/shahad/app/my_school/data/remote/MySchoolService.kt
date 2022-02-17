package com.shahad.app.my_school.data.remote

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.SchoolDto
import retrofit2.Response
import retrofit2.http.*

interface MySchoolService{

    @POST("/user/create")
    suspend fun addUser(
        @Query("role") role: String,
        @Body registerBody: JsonElement
    ): Response<BaseResponse<AuthenticationResponse>>

    @POST("/user/login")
    suspend fun loginUser(@Body loginBody: JsonElement):  Response<BaseResponse<AuthenticationResponse>>

    @GET("/teacher/classes")
    suspend fun getTeacherClasses(): Response<BaseResponse<List<ClassList>>>

    @GET("/manger/schools")
    suspend fun getMangerSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/classes")
    suspend fun getMangerClasses(): Response<BaseResponse<List<ClassList>>>

    @POST("/school/new")
    suspend fun createSchool(@Query("name") schoolName: String): Response<Int>

    @POST("/class/new")
    suspend fun createClass(@Body requestBody: JsonElement): Response<Int>

}