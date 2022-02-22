package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow

interface MySchoolRepository{

    fun addUser(role: String,registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun getTeacherClasses(): Flow<State<BaseResponse<List<ClassList>>?>>
    fun getTeacherSchools(): Flow<State<BaseResponse<List<SchoolDto>>?>>

    fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>>
    fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>>

    fun createSchool(schoolName: String): Flow<State<BaseResponse<SchoolDto>?>>

    fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto>?>>

    fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun joinTeacher(schoolName: String): Flow<State<BaseResponse<String>?>>

    fun getSchoolStudents(schoolName: String): Flow<State<BaseResponse<List<UserDto>>?>>

    fun getSchoolTeachers(schoolName: String): Flow<State<BaseResponse<List<UserDto>>?>>

}