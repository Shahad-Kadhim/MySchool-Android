package com.shahad.app.my_school.data.remote

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.*
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
    suspend fun getTeacherClasses(@Query("search" ) searchKey: String?): Response<BaseResponse<List<ClassList>>>

    @GET("/teacher/schools")
    suspend fun getTeacherSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/schools")
    suspend fun getMangerSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/classes")
    suspend fun getMangerClasses(): Response<BaseResponse<List<ClassList>>>

    @GET("/school/students")
    suspend fun getSchoolStudent(
        @Query("school_name") schoolName: String,
        @Query("search" ) searchKey: String?
    ): Response<BaseResponse<List<UserDto>>>

    @GET("/school/teachers")
    suspend fun getSchoolTeachers(
        @Query("school_name") schoolName: String,
        @Query("search" ) searchKey: String?
    ): Response<BaseResponse<List<UserDto>>>

    @POST("/school/new")
    suspend fun createSchool(@Query("name") schoolName: String): Response<BaseResponse<SchoolDto>>

    @POST("/class/new")
    suspend fun createClass(@Body requestBody: JsonElement): Response<BaseResponse<ClassDto>>

    @POST("/student/joinSchool")
    suspend fun addStudentToSchool(@Body requestBody: JsonElement): Response<BaseResponse<String>>

    @POST("/teacher/joinSchool")
    suspend fun addTeacherToSchool(@Body requestBody: JsonElement): Response<BaseResponse<String>>
}