package com.shahad.app.my_school.ui.register

import androidx.lifecycle.*
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
class RegisterViewModel @Inject constructor(
    val repository: MySchoolRepository
): BaseViewModel(){

    val name = MutableStateFlow("")
    val password = MutableStateFlow("")
    val phone = MutableStateFlow<Long?>(null)
    val teachingSpecialization = MutableStateFlow("")

    private val _signUpState = MutableLiveData<State<String?>>()

    val signUpState: LiveData<State<String?>> = _signUpState

    val whenSuccess: LiveData<String> =
        MediatorLiveData<String>().apply {
            addSource(_signUpState){ state->
                takeIf { state is State.Success<*> }?.let {
                    this.postValue(state.toData())
                }
            }
        }

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
                repository.addTeacher(body).collect{ state ->
                    _signUpState.postValue(state)
                }
            }
        }
    }

    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}