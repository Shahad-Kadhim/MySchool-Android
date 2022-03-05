package com.shahad.app.my_school.ui.add.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.ui.add.BaseNewViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CreateSchoolViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseNewViewModel() {

    private val _createRequestStatus = MutableLiveData<State<BaseResponse<SchoolDto>?>>()

    val onSuccessCreated = Transformations.map(_createRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    override fun onClickAdd(){
        name.value?.takeIf { it.isNotBlank() }?.let {
            viewModelScope.launch {
                repository.createSchool(it).collect {
                    _createRequestStatus.postValue(it)
                }
            }
        }
    }

}