package com.shahad.app.my_school.ui.add.student

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.ui.add.BaseNewViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddStudentToSchoolViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dataClassParser: DataClassParser
): BaseNewViewModel() {

    private val _addRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()
    val addRequestStatus: LiveData<State<BaseResponse<String>?>> = _addRequestStatus

    val onSuccessJoined = Transformations.map(_addRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    val schoolId =savedStateHandle.get<String>("SchoolId")


    override fun onClickAdd(){
        getRequestBody()?.let {
            viewModelScope.launch {
                repository.addStudentToSchool(it).collect {
                    _addRequestStatus.postValue(it)
                }
            }
        }
    }

    private fun getRequestBody() =
        takeIf { !(name.value.isNullOrBlank())&& !((schoolId).isNullOrBlank()) }?.let {
            dataClassParser.parseToJson(
                AddUserBody(
                    name.value!!,
                    schoolId!!
                )
            )
        }

}