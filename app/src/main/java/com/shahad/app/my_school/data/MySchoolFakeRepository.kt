package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MySchoolFakeRepository @Inject constructor(): MySchoolRepository{
    override fun addUser(role: String , registerBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        getFakeFlow(AuthenticationResponse(role,""))

    override fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
       getFakeFlow(AuthenticationResponse("MANGER",""))

    override fun getTeacherClasses(searchKey: String?): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow(listOf())

    override fun getTeacherSchools(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        getFakeFlow(listOf())

    override fun getMangerSchool(): Flow<State<BaseResponse<List<SchoolDto>>?>> =
        getFakeFlow(listOf())

    override fun getMangerClasses(): Flow<State<BaseResponse<List<ClassList>>?>> =
        getFakeFlow (listOf())

    override fun createSchool(schoolName: String)=
        getFakeFlow(SchoolDto("","",""))

    override fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto>?>> =
        getFakeFlow(ClassDto("","","","",2))

    override fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        getFakeFlow("")

    override fun addTeacherToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        getFakeFlow("")

    override fun getSchoolStudents(
        schoolName: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserSelected>>?>> =
        getFakeFlow (listOf())

    override fun getSchoolTeachers(
        schoolName: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserSelected>>?>> =
        getFakeFlow (listOf())

    override fun getStudentsNotInClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>> =
        getFakeFlow(listOf())

    override fun addStudentToClass(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        getFakeFlow("")

    override fun getMemberClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>> =
        getFakeFlow(listOf())

    private  fun <T> getFakeFlow(respond: T)=
        flow{
            emit(State.Loading)
            kotlinx.coroutines.delay(500)
            emit( State.Success(BaseResponse(200,respond)))
        }

}