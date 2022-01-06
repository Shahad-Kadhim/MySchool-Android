package com.shahad.app.my_school.ui.register

import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val repository: MySchoolRepository
): BaseViewModel(){

    val name = MutableStateFlow("")
    val password = MutableStateFlow("")
    val phone = MutableStateFlow<Long?>(null)
    val teachingSpecialization = MutableStateFlow("")

    private val _clickSignUpEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickSignUpEvent: StateFlow<Event<Boolean>?> = _clickSignUpEvent

    private val _clickNavLoginEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavLoginEvent: StateFlow<Event<Boolean>?> = _clickNavLoginEvent

    fun onClickSignUp(){
        _clickSignUpEvent.tryEmit(Event(true))
    }

    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}