package com.shahad.app.my_school.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.register.TeacherRegisterBody
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: MySchoolRepository
): BaseViewModel(){

    val name = MutableStateFlow("")
    val password = MutableStateFlow("")
    val userType = MutableStateFlow(UserType.TEACHER)

    private val _clickLoginEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickLoginEvent: StateFlow<Event<Boolean>?> = _clickLoginEvent

    private val _clickNavSignUpEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavSignUpEvent: StateFlow<Event<Boolean>?> = _clickNavSignUpEvent

    fun onClickLogin(){

        val body = DataClassParser.parseToJson(
            LoginBody(
                name.value,
                password.value,
            )
        )

        when(userType.value){
            UserType.TEACHER -> loginTeacher(body)
            UserType.STUDENT -> loginStudent(body)
            UserType.MANGER -> loginManger(body)
        }
    }

    private fun loginManger(body: JsonElement) {

    }

    private fun loginStudent(body: JsonElement) {

    }

    private fun loginTeacher(body: JsonElement) {
        viewModelScope.launch {
            repository.loginTeacher(body)
            _clickLoginEvent.tryEmit(Event(true))
        }

    }

    fun onClickNavSignUp(){
        _clickNavSignUpEvent.tryEmit(Event(true))
    }

}