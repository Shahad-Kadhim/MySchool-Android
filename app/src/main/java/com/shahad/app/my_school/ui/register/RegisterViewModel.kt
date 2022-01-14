package com.shahad.app.my_school.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
        phone.value?.let {
            val body =DataClassParser.parseToJson(
                TeacherRegisterBody(
                name.value,
                password.value,
                teachingSpecialization.value,
                it
                )
            )
            viewModelScope.launch {
                repository.addTeacher(body)
                _clickSignUpEvent.tryEmit(Event(true))
            }
        }
    }

    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}