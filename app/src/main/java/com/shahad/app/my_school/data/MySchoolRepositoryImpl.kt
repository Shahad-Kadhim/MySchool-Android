package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.MySchoolService
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MySchoolRepositoryImpl @Inject constructor(
    private val dao: MySchoolDao,
    private val apiService: MySchoolService,
): MySchoolRepository{

    override fun addUser(role: String,registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        wrapWithFlow { apiService.addUser(role,registerBody) }

    override fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        wrapWithFlow { apiService.loginUser(loginBody) }

    override fun getTeacherClasses(searchKey: String?): Flow<State<BaseResponse<List<ClassList>>?>> =
        wrapWithFlow { apiService.getTeacherClasses(searchKey) }

    override fun getTeacherSchools(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        wrapWithFlow { apiService.getTeacherSchools() }

    override fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        wrapWithFlow { apiService.getMangerSchools() }

    override fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        wrapWithFlow { apiService.getMangerClasses() }

    override fun createSchool(schoolName: String): Flow<State<BaseResponse<SchoolDto>?>> =
        wrapWithFlow{ apiService.createSchool(schoolName) }

    override fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto>?>> =
        wrapWithFlow{ apiService.createClass(requestBody) }

    override fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.addStudentToSchool(requestBody) }

    override fun addTeacherToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.addTeacherToSchool(requestBody) }

    override fun getSchoolStudents(
        schoolName: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserDto>>?>> =
        wrapWithFlow { apiService.getSchoolStudent(schoolName,searchKey) }

    override fun getSchoolTeachers(
        schoolName: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserDto>>?>> =
        wrapWithFlow { apiService.getSchoolTeachers(schoolName,searchKey) }

    private fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<State<T?>> {
        return flow {
            emit(State.Loading)
            try {
                emit(checkIsSuccessful(function()))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }

    private fun <T> checkIsSuccessful(response: Response<T>): State<T?> =
        when {
            response.isSuccessful -> {
                State.Success(response.body())
            }
            response.code()==401 -> {
                State.UnAuthorization
            }
            else -> {
                State.Error(response.message())
            }
        }
}