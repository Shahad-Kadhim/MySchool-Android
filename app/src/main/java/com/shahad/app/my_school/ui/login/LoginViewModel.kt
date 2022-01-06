package com.shahad.app.my_school.ui.login

import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        _clickLoginEvent.tryEmit(Event(true))
    }

    fun onClickNavSignUp(){
        _clickNavSignUpEvent.tryEmit(Event(true))
    }

}