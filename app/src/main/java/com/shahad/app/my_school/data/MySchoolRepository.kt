package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow

interface MySchoolRepository{

    fun addUser(role: String,registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun getTeacherClasses(): Flow<State<BaseResponse<List<ClassList>>?>>

    fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>>
    fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>>

    fun createSchool(schoolName: String): Flow<State<Int?>>

    fun createClass(requestBody: JsonElement): Flow<State<Int?>>

}