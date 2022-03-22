package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MySchoolRepository{

    fun addUser(role: String,registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>>

    fun getTeacherClasses(searchKey: String? = null): Flow<List<ClassList>>

    fun getTeacherSchools(): Flow<List<School>>

    fun getMangerSchool(): Flow<List<School>>

    fun getStudentSchools(): Flow<State<BaseResponse<List<SchoolDto>>?>>

    fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>>

    fun createSchool(schoolName: String): Flow<State<BaseResponse<SchoolDto>?>>

    fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto>?>>

    fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>
    fun addTeacherToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun getSchoolStudents(schoolId: String,searchKey: String? = null): Flow<State<BaseResponse<List<UserSelected>>?>>

    fun getSchoolTeachers(schoolId: String, searchKey: String? = null): Flow<State<BaseResponse<List<UserSelected>>?>>

    fun getStudentsNotInClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>>

    fun addStudentToClass(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun getMemberClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>>

    fun createPost(parts: HashMap<String,RequestBody>): Flow<State<BaseResponse<String>?>>

    fun getPostInClass(classId: String): Flow<State<BaseResponse<List<PostDto>>?>>

    fun getStudentInfo(id: String? = null): Flow<State<BaseResponse<StudentDto?>?>>

    fun getTeacherInfo(id: String? = null): Flow<State<BaseResponse<TeacherDto?>?>>

    fun getMangerInfo(): Flow<State<BaseResponse<MangerInfoDto?>?>>

    fun removeStudentFromClass(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun removeStudentFromSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun removeTeacherFromSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>

    fun deleteSchool(schoolId: String): Flow<State<BaseResponse<String>?>>

    suspend fun refreshMangerSchool()
    suspend fun refreshTeacherSchool()
    suspend fun refreshTeacherClasses(searchKey: String?)
}