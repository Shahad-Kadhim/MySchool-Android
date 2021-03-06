package com.shahad.app.my_school.data.remote

import com.shahad.app.my_school.data.remote.response.DutySubmit
import com.shahad.app.my_school.data.remote.response.PostDetailsDto
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

    //TODO LATER IN SERVER SIDE
    @GET("/teacher/classes")
    suspend fun getTeacherClasses(@Query("search" ) searchKey: String? =null): Response<BaseResponse<List<ClassDto>>>

    @GET("/teacher/schools")
    suspend fun getTeacherSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/schools")
    suspend fun getMangerSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/student/schools")
    suspend fun getStudentSchools(): Response<BaseResponse<List<SchoolDto>>>

    @GET("/manger/classes")
    suspend fun getMangerClasses(): Response<BaseResponse<List<ClassDto>>>

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
    suspend fun createClass(@Body requestBody: JsonElement): Response<BaseResponse<ClassDto2>>

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
        @Part("jsonRequest") jsonRequest: RequestBody,
        @Part image: MultipartBody.Part? =null
    ): Response<BaseResponse<String>>

    @GET("post/getPost")
    suspend fun getPostsInClass(@Query("classId") classId: String): Response<BaseResponse<List<PostDto>>>

    @GET("student/info")
    suspend fun getStudentInfo(
        @Query("id") id: String? =null
    ): Response<BaseResponse<StudentDto?>>


    @GET("teacher/info")
    suspend fun getTeacherInfo(
        @Query("id") id: String? =null
    ): Response<BaseResponse<TeacherDto?>>

    @GET("manger/info")
    suspend fun getMangerInfo(): Response<BaseResponse<MangerInfoDto?>>

    @POST("class/removeMember")
    suspend fun removeMemberFromClass(@Body requestBody: JsonElement): Response<BaseResponse<String>>

    @POST("school/delete")
    suspend fun deleteSchool(@Query("id") id: String): Response<BaseResponse<String>>

    @POST("school/removeStudent")
    suspend fun removeStudentFromSchool(@Body requestBody: JsonElement): Response<BaseResponse<String>>

    @POST("school/removeTeacher")
    suspend fun removeTeacherFromSchool(@Body requestBody: JsonElement): Response<BaseResponse<String>>

    @GET("post/{postId}")
    suspend fun getPostDetails(@Path("postId") postId: String): Response<BaseResponse<PostDetailsDto>>

    @POST("comment/create")
    suspend fun createComment(
        @Query("postId") postId: String,
        @Query("content") content: String
    ): Response<BaseResponse<String>>

    @GET("{postId}/comment")
    suspend fun getCommentInPost(
        @Path("postId") postId: String
    ): Response<BaseResponse<List<CommentDto>>>

    @GET("student/classes")
    suspend fun getStudentClasses(
        @Query("id") studentId: String?= null,
        @Query("search" ) searchKey: String?= null
    ): Response<BaseResponse<List<ClassDto>>>

    @Multipart
    @POST("duty/{dutyId}/addSolution")
    suspend fun addSolution(
        @Path("dutyId") dutyId: String,
        @Part solutionImage: MultipartBody.Part? =null
    ): Response<BaseResponse<String>>

    @GET("duty/getSolution")
    suspend fun getSolution(
        @Query("dutyId") dutyId: String,
        @Query("studentId") studentId: String? =null
    ): Response<BaseResponse<DutySubmit?>>

    @GET("duty/getSolutions")
    suspend fun getSolutionsForDuty(
        @Query("dutyId") dutyId: String,
    ): Response<BaseResponse<List<DutySubmit>>>

    @GET("teacher/getDuties")
    suspend fun getAssignmentForTeacher(
        @Query("teacherId") teacherId: String?= null ,
    ): Response<BaseResponse<List<AssignmentDto>>>

    @GET("/student/assignments")
    suspend fun getAssignmentForStudent(): Response<BaseResponse<List<AssignmentDto>>>

    @GET("/notification")
    suspend fun getNotification(): Response<BaseResponse<List<NotificationDto>>>


    @GET("/student/dutiesStatistic")
    suspend fun getDutiesStatistic(): Response<BaseResponse<String>>

}