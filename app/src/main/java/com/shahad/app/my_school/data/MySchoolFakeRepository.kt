package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MySchoolFakeRepository @Inject constructor(): MySchoolRepository{
    override fun addUser(role: String , registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        getFakeFlow(BaseResponse(200, AuthenticationResponse(role,"")))

    override fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
       getFakeFlow(BaseResponse(200,AuthenticationResponse("MANGER","")))

    override fun getTeacherClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow(BaseResponse(4, listOf()))

    override fun getTeacherSchools(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        getFakeFlow(BaseResponse(200, listOf()))

    override fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        getFakeFlow(BaseResponse(200,listOf<SchoolDto>()))

    override fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow(BaseResponse(200, listOf()))

    override fun createSchool(schoolName: String)=
        getFakeFlow(BaseResponse(200,SchoolDto("","","")))

    override fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto>?>> =
        getFakeFlow(BaseResponse(200,ClassDto("","","","",2)))

    override fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        getFakeFlow(BaseResponse(200,""))

    override fun joinTeacher(schoolName: String): Flow<State<BaseResponse<String>?>> =
        getFakeFlow(BaseResponse(200,"Fake Join HAHAHAHAHAHA"))

    override fun getSchoolStudents(schoolName: String): Flow<State<BaseResponse<List<UserDto>>?>> =
        getFakeFlow(BaseResponse(200, listOf()))

    override fun getSchoolTeachers(schoolName: String): Flow<State<BaseResponse<List<UserDto>>?>> =
        getFakeFlow(BaseResponse(200, listOf()))

    private  fun <T> getFakeFlow(respond: T)=
        flow{
            emit(State.Loading)
            kotlinx.coroutines.delay(500)
            emit( State.Success(respond))
        }

}