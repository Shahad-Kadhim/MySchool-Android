package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.MySchoolService
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.SchoolDto
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

    override fun addTeacher(registerBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.addTeacher(registerBody) }

    override fun loginTeacher(loginBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.loginTeacher(loginBody) }

    override fun addStudent(registerBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.addStudent(registerBody) }

    override fun loginStudent(loginBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.loginStudent(loginBody) }

    override fun addManger(registerBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.addManger(registerBody) }

    override fun loginManger(loginBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.loginManger(loginBody) }

    override fun getTeacherClasses(): Flow<State<BaseResponse<List<String>>?>> =
        wrapWithFlow { apiService.getTeacherClasses() }

    override fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        wrapWithFlow { apiService.getMangerSchools() }

    override fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        wrapWithFlow { apiService.getMangerClasses() }


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