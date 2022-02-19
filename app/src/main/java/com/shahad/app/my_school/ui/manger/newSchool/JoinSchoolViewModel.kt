package com.shahad.app.my_school.ui.manger.newSchool

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class JoinSchoolViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseNewSchoolViewModel() {

    private val _joinRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()
    val joinRequestStatus: LiveData<State<BaseResponse<String>?>> = _joinRequestStatus

    val onSuccessJoined = Transformations.map(joinRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    override fun onClickAddSchool(){
        viewModelScope.launch{
            schoolName.value?.takeIf { it.isNotBlank() }?.let { schoolName ->
                repository.joinTeacher(schoolName).collect {
                    _joinRequestStatus.postValue(it)
                }
            }
        }
    }

}