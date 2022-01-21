package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.MySchoolService
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MySchoolRepositoryImpl @Inject constructor(
    val dao: MySchoolDao,
    val apiService: MySchoolService,
): MySchoolRepository{

    override fun addTeacher(registerBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.addTeacher(registerBody) }

    override fun loginTeacher(loginBody: JsonElement): Flow<State<String?>> =
        wrapWithFlow { apiService.loginTeacher(loginBody) }

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
        if (response.isSuccessful) {
            State.Success(response.body())
        } else {
            State.Error(response.message())
        }
}