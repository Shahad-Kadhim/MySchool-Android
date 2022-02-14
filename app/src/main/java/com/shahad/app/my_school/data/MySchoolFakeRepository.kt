package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MySchoolFakeRepository @Inject constructor(): MySchoolRepository{
    override fun addTeacher(registerBody: JsonElement): Flow<State<String?>> =
        getFakeFlow("hfjhgdgjdhgjd")

    override fun loginTeacher(loginBody: JsonElement): Flow<State<String?>> =
       getFakeFlow("hfjhgdgjdhgjd")

    override fun addStudent(registerBody: JsonElement): Flow<State<String?>> =
        getFakeFlow("hfjhgdgjdhgjd")

    override fun loginStudent(loginBody: JsonElement): Flow<State<String?>> =
        getFakeFlow("hfjhgdgjdhgjd")

    override fun addManger(registerBody: JsonElement): Flow<State<String?>> =
        getFakeFlow("hfjhgdgjdhgjd")


    override fun loginManger(loginBody: JsonElement): Flow<State<String?>> =
        getFakeFlow("hfjhgdgjdhgjd")


    override fun getTeacherClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow(BaseResponse(4, listOf()))

    override fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        getFakeFlow(BaseResponse(200,listOf<SchoolDto>()))

    override fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow(BaseResponse(200, listOf()))

    override fun createSchool(schoolName: String)=
        getFakeFlow(2)

//    override fun createClass(className: String): Flow<State<Int?>> =
//        getFakeFlow(2)

    private  fun <T> getFakeFlow(respond: T)=
        flow{
            emit(State.Loading)
            kotlinx.coroutines.delay(500)
            emit( State.Success(respond))
        }

}