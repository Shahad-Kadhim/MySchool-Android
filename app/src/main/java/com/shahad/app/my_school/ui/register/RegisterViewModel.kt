package com.shahad.app.my_school.ui.register

import androidx.core.text.isDigitsOnly
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
    val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseViewModel(){

    val name = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    val phone = MutableLiveData<Long?>()
    val teachingSpecialization = MutableLiveData<String?>()
    val note = MutableLiveData<String?>()
    val stage = MutableLiveData<Int?>()
    val age = MutableLiveData<Int?>()
    val role = MutableLiveData<Role>(Role.TEACHER)

    val jj= MediatorLiveData<TeacherRegisterBody?>().apply {
        addSource(name,){
            validateField()
        }
        addSource(password){
            validateField()
        }
        addSource(phone){

        }


    }
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
        takeIf { validateField() }?.let{
            when(role.value!!){
                Role.TEACHER -> signUpTeacher()
                Role.STUDENT -> signUpStudent()
                Role.MANGER -> signUpManger()
              }
        }
    }

    private fun signUpManger() {

    }

    private fun signUpStudent() {
        val body =dataClassParser.parseToJson(
            TeacherRegisterBody(
                name.value!!,
                password.value!!,
                teachingSpecialization.value!!,
                phone.value!!.toLong()
            )
        )
        viewModelScope.launch {
            repository.addTeacher(body).collect{ state ->
                _signUpState.postValue(state)
            }
        }
    }

    private fun signUpTeacher() {
        val body = dataClassParser.parseToJson(
                TeacherRegisterBody(
                    name.value!!,
                    password.value!!,
                    teachingSpecialization.value!!,
                    phone.value!!.toLong()
                )
        )
        viewModelScope.launch {
            repository.addTeacher(body).collect{ state ->
                _signUpState.postValue(state)
            }
        }
    }

    private fun validateField(
//        phoneValue:Long = phone.value
    ) = true
//        when(role.value!!) {
//            Role.TEACHER -> {
//                name.value.isNullOrBlank()
//                        && (password.value ?: "").length >= 8
//            }
//            Role.STUDENT->{
//                name.value.isNullOrBlank()
//                        && (password.value ?: "").length >= 8
//                        && phone.value?.length == 11
//                        && (phone.value ?: "").isDigitsOnly()
//                        && age.value?.toIntOrNull() in 6..19
//                        && stage.value?.toIntOrNull() in 1..13
//            }
//            Role.MANGER-> {
//                name.value.isNullOrBlank() && (password.value ?: "").length >= 8
//            }
//        }


    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}


//        phone.value?.toLongOrNull()?.let {
//            val body =DataClassParser.parseToJson(
//                TeacherRegisterBody(
//                name.value!!,
//                password.value!!,
//                teachingSpecialization.value!!,
//                it
//                )
//                                             )
//            viewModelScope.launch {
//                repository.addTeacher(body).collect{ state ->
//                    _signUpState.postValue(state)
//                }
//            }
//        }
