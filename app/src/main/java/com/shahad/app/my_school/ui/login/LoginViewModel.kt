package com.shahad.app.my_school.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseViewModel(){

    val name = MutableStateFlow("")
    val password = MutableStateFlow("")
    val role = MutableStateFlow(Role.TEACHER)

    private val _loginState = MutableLiveData<State<String?>>()
    val loginState: LiveData<State<String?>> = _loginState

    private val _clickNavSignUpEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavSignUpEvent: StateFlow<Event<Boolean>?> = _clickNavSignUpEvent

    val whenSuccess: LiveData<Pair<String,String>> =
        MediatorLiveData<Pair<String,String>>().apply {
            addSource(_loginState){ state->
                takeIf { state is State.Success<*> }?.let {
                    this.postValue(Pair(role.value.name,state.toData().toString()))
                }
            }
        }

    fun onClickLogin(){
        when(role.value){
            Role.TEACHER -> login(repository::loginTeacher)
            Role.STUDENT -> login(repository::loginStudent)
            Role.MANGER -> login(repository::loginManger)
        }
    }

    private fun login(loginRequest: (JsonElement) -> Flow<State<String?>>) {
        dataClassParser.parseToJson(
            LoginBody(
                name.value,
                password.value
            )
        ).also{
            viewModelScope.launch {
                loginRequest(it).collect {
                    _loginState.postValue(it)
                }
            }
        }
    }

    fun onClickNavSignUp(){
        _clickNavSignUpEvent.tryEmit(Event(true))
    }

}