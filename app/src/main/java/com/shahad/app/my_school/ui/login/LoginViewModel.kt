package com.shahad.app.my_school.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.response.BaseResponse
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

    private val _loginState = MutableLiveData<State<BaseResponse<AuthenticationResponse>?>>()
    val loginState: LiveData<State<BaseResponse<AuthenticationResponse>?>> = _loginState

    private val _clickNavSignUpEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavSignUpEvent: StateFlow<Event<Boolean>?> = _clickNavSignUpEvent

    val whenSuccess: LiveData<AuthenticationResponse> =
        MediatorLiveData<AuthenticationResponse>().apply {
            addSource(_loginState){ state->
                takeIf { state is State.Success<*> }?.let {
                    this.postValue((state.toData() as BaseResponse<AuthenticationResponse>).data)
                }
            }
        }

    fun onClickLogin(){
        dataClassParser.parseToJson(
            LoginBody(
                name.value,
                password.value
            )
        ).also{
            viewModelScope.launch {
                repository.loginUser(it).collect {
                    _loginState.postValue(it)
                }
            }
        }
    }


    fun onClickNavSignUp(){
        _clickNavSignUpEvent.tryEmit(Event(true))
    }

}