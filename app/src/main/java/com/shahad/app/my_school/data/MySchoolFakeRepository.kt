package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MySchoolFakeRepository @Inject constructor(): MySchoolRepository{
    override fun addTeacher(registerBody: JsonElement): Flow<State<String?>> =
        getFakFlow("hfjhgdgjdhgjd")

    override fun loginTeacher(loginBody: JsonElement): Flow<State<String?>> =
       getFakFlow("hfjhgdgjdhgjd")

    override fun addStudent(registerBody: JsonElement): Flow<State<String?>> =
        getFakFlow("hfjhgdgjdhgjd")

    override fun loginStudent(loginBody: JsonElement): Flow<State<String?>> =
        getFakFlow("hfjhgdgjdhgjd")

    override fun addManger(registerBody: JsonElement): Flow<State<String?>> =
        getFakFlow("hfjhgdgjdhgjd")


    override fun loginManger(loginBody: JsonElement): Flow<State<String?>> =
        getFakFlow("hfjhgdgjdhgjd")


    override fun getTeacherClasses(): Flow<State<BaseResponse<List<String>>?>> =
        getFakFlow(BaseResponse(4, listOf("")))



    private  fun <T> getFakFlow(respond: T)=
        flow{
            emit(State.Loading)
            kotlinx.coroutines.delay(500)
            emit( State.Success(respond))
        }

}