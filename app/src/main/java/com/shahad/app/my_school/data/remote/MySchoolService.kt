package com.shahad.app.my_school.data.remote

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("/student/schools")
    suspend fun getStudentSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/classes")
    suspend fun getMangerClasses(): Response<BaseResponse<List<ClassList>>>

    @GET("/school/students")
    suspend fun getSchoolStudent(
        @Query("school_id") schoolId: String,
        @Query("search" ) searchKey: String?
    ): Response<BaseResponse<List<UserDto>>>

    @GET("/school/teachers")
    suspend fun getSchoolTeachers(
        @Query("school_id") schoolId: String,
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

    @GET("/studentSchool")
    suspend fun getStudentsInSchoolNotInClass(@Query("classId") classId: String): Response<BaseResponse<List<UserDto>>>

    @POST("/class/addMember")
    suspend fun addStudentsToClass(@Body requestBody: JsonElement): Response<BaseResponse<String>>

    @GET("/class/{classId}/members")
    suspend fun getClassMember(@Path("classId") classId: String): Response<BaseResponse<List<UserDto>>>

    @Multipart
    @POST("post/create")
    suspend fun createPost(
        @PartMap parts: HashMap<String,RequestBody>
//        @Part("jsonRequest") requestBody: MultipartBody.Part                            ,
//        @Part("image") file: MultipartBody.Part?
    ): Response<BaseResponse<String>>

    @GET("post/getPost")
    suspend fun getPostsInClass(@Query("classId") classId: String): Response<BaseResponse<List<PostDto>>>

    @GET("student/info")
    suspend fun getStudentInfo(
        @Query("id") id: String? =null
    ): Response<BaseResponse<StudentDto?>>

}