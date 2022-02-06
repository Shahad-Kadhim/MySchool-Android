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

    val vaild= MediatorLiveData<Boolean>().apply {
        addSource(name){
            this.postValue(validateField())
        }
        addSource(password){
            this.postValue(validateField())
        }
        addSource(phone){
            this.postValue(validateField())
        }

        addSource(age){
            this.postValue(validateField())
        }

        addSource(stage){
            this.postValue(validateField())
        }
        addSource(role){
            this.postValue(validateField())
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

    }

    private fun signUpTeacher() {
        val body =dataClassParser.parseToJson(
            TeacherRegisterBody(
                name.value!!,
                password.value!!,
                teachingSpecialization.value!!,
                phone.value!!
            )
        )
        viewModelScope.launch {
            repository.addTeacher(body).collect{ state ->
                _signUpState.postValue(state)
            }
        }
    }

    private fun validateField() =
        when(role.value!!) {
            Role.TEACHER -> {
                !(name.value.isNullOrBlank())
                        && (password.value ?: "").length >= 8
                        && phone.value !=null
            }
            Role.STUDENT->{
                !(name.value.isNullOrBlank())
                    && (password.value ?: "").length >= 8
                        && phone.value !=null
                        && age.value !=null
                        && stage.value !=null
            }
            Role.MANGER-> {
                !(name.value.isNullOrBlank())
                        && (password.value ?: "").length >= 8
            }
        }


    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}
