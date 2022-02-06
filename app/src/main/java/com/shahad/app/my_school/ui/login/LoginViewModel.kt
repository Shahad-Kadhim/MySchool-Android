package com.shahad.app.my_school.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val userType = MutableStateFlow(UserType.TEACHER)

    private val _loginState = MutableLiveData<State<String?>>()
    val loginState: LiveData<State<String?>> = _loginState

    private val _clickNavSignUpEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavSignUpEvent: StateFlow<Event<Boolean>?> = _clickNavSignUpEvent

    val whenSuccess: LiveData<String> =
        MediatorLiveData<String>().apply {
            addSource(_loginState){ state->
                takeIf { state is State.Success<*> }?.let {
                    this.postValue(state.toData())
                }
            }
        }

    fun onClickLogin(){

        val body = dataClassParser.parseToJson(
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
            repository.loginTeacher(body).collect {
                _loginState.postValue(it)
            }
        }

    }

    fun onClickNavSignUp(){
        _clickNavSignUpEvent.tryEmit(Event(true))
    }

}