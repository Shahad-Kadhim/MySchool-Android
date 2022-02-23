package com.shahad.app.my_school.ui.add.teacher

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.ui.add.BaseNewViewModel
import com.shahad.app.my_school.ui.add.student.AddUserBody
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser,
    private val savedStateHandle: SavedStateHandle
): BaseNewViewModel() {

    private val _joinRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()
    val joinRequestStatus: LiveData<State<BaseResponse<String>?>> = _joinRequestStatus

    val onSuccessJoined = Transformations.map(joinRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }
    val schoolName =savedStateHandle.get<String>("SchoolName")

    override fun onClickAdd(){
        getRequestBody()?.let { body ->
            viewModelScope.launch{
                repository.addTeacherToSchool(body).collect { state ->
                    _joinRequestStatus.postValue(state)
                }
            }
        }
    }

    private fun getRequestBody() =
        takeIf { !(name.value.isNullOrBlank())&& !((schoolName).isNullOrBlank()) }?.let {
            dataClassParser.parseToJson(
                AddUserBody(
                    name.value!!,
                    schoolName!!
                )
            )
        }

}