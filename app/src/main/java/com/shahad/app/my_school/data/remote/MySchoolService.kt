package com.shahad.app.my_school.data.remote

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.SchoolDto
import retrofit2.Response
import retrofit2.http.*

interface MySchoolService{

    @POST("/teacher/new")
    suspend fun addTeacher(@Body registerBody: JsonElement): Response<String>

    @POST("/teacher/login")
    suspend fun loginTeacher(@Body loginBody: JsonElement):  Response<String>

    @POST("/student/register")
    suspend fun addStudent(@Body registerBody: JsonElement): Response<String>

    @POST("/student/login")
    suspend fun loginStudent(@Body loginBody: JsonElement):  Response<String>

    @POST("/manger/new")
    suspend fun addManger(@Body registerBody: JsonElement): Response<String>

    @POST("/manger/login")
    suspend fun loginManger(@Body loginBody: JsonElement):  Response<String>

    @GET("/teacher/classes")
    suspend fun getTeacherClasses(): Response<BaseResponse<List<ClassList>>>

    @GET("/manger/schools")
    suspend fun getMangerSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/classes")
    suspend fun getMangerClasses(): Response<BaseResponse<List<ClassList>>>

    @POST("/school/new")
    suspend fun createSchool(@Query("name") schoolName: String): Response<Int>

}