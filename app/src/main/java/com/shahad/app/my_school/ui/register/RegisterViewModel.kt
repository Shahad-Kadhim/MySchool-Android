package com.shahad.app.my_school.ui.register

import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonElement
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
class RegisterViewModel @Inject constructor(
    private val repository: MySchoolRepository,
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

    val valid= MediatorLiveData<Boolean>().apply {
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

    private val _signUpState = MutableLiveData<State<BaseResponse<AuthenticationResponse>?>>()

    val signUpState: LiveData<State<BaseResponse<AuthenticationResponse>?>> = _signUpState

    val whenSuccess: LiveData<AuthenticationResponse> =
        MediatorLiveData<AuthenticationResponse>().apply {
            addSource(_signUpState){ state->
                takeIf { state is State.Success<*> }?.let {
                    this.postValue((state.toData() as BaseResponse<AuthenticationResponse>).data)
                }
            }
        }

    private val _clickNavLoginEvent = MutableStateFlow<Event<Boolean>?>(null)
    val clickNavLoginEvent: StateFlow<Event<Boolean>?> = _clickNavLoginEvent

    fun onClickSignUp(){
        onGetFirebaseToken { firebaseToken ->
            takeIf { validateField() }?.let{
                role.value?.let { currentRole ->
                    signUpUser(currentRole,getRequestBody(currentRole,firebaseToken))
                }
            }
        }
    }


    private fun onGetFirebaseToken(registerRequest:(String)->Unit){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            registerRequest(task.result)
        })
    }

    private fun getRequestBody(role: Role,firebaseToken: String) =
        when(role){
            Role.TEACHER -> getTeacherRequestBody(firebaseToken)
            Role.STUDENT -> getStudentRequestBody(firebaseToken)
            Role.MANGER -> getMangerRequestBody(firebaseToken)
        }


    private fun signUpUser(currentRole: Role ,body: JsonElement){
        viewModelScope.launch{
            repository.addUser(currentRole.name, body).collect {
                _signUpState.postValue(it)
            }
        }
    }

    private fun getMangerRequestBody(firebaseToken: String) =
        dataClassParser.parseToJson(
            MangerRegisterBody(
                name.value!!,
                password.value!!,
                phone.value!!,
                firebaseToken
            )
        )


    private fun getStudentRequestBody(firebaseToken: String) =
        dataClassParser.parseToJson(
            StudentRegisterBody(
                name.value!!,
                password.value!!,
                phone.value!!,
                note.value ?: "",
                age.value!!,
                stage.value!!,
                firebaseToken
            )
        )


    private fun getTeacherRequestBody(firebaseToken: String) =
        dataClassParser.parseToJson(
            TeacherRegisterBody(
                name.value!!,
                password.value!!,
                teachingSpecialization.value!!,
                phone.value!!,
                firebaseToken
            )
        )



    private fun validateField() =
        when(role.value!!) {
            Role.TEACHER -> {
                isValidNamePasswordPhoneField()
                        && !(teachingSpecialization.value.isNullOrBlank())
            }
            Role.STUDENT->{
                isValidNamePasswordPhoneField()
                        && age.value !=null
                        && stage.value !=null
            }
            Role.MANGER-> {
                isValidNamePasswordPhoneField()
            }
        }


    private fun isValidNamePasswordPhoneField() =
        !(name.value.isNullOrBlank())
                && (password.value ?: "").length >= 8
                && phone.value !=null


    fun onClickNavLogin(){
        _clickNavLoginEvent.tryEmit(Event(true))
    }

}
