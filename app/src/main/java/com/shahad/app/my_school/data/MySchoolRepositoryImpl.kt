package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.MySchoolService
import retrofit2.Response
import javax.inject.Inject

class MySchoolRepositoryImpl @Inject constructor(
    val dao: MySchoolDao,
    val apiService: MySchoolService,
): MySchoolRepository{

    override suspend fun addTeacher(registerBody: JsonElement): Response<String> =
        apiService.addTeacher(registerBody)

    override suspend fun loginTeacher(loginBody: JsonElement):  Response<String> =
        apiService.loginTeacher(loginBody)


}