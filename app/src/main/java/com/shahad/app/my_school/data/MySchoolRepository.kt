package com.shahad.app.my_school.data

import com.google.gson.JsonElement
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow

interface MySchoolRepository{

    fun addTeacher(registerBody: JsonElement): Flow<State<String?>>

    fun loginTeacher(loginBody: JsonElement): Flow<State<String?>>

}