package com.shahad.app.my_school.ui.add.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStudentsToCLassViewModel @Inject constructor(
    val repository: MySchoolRepository
): BaseViewModel() {
    private val _addRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()
    val addRequestStatus: LiveData<State<BaseResponse<String>?>> = _addRequestStatus

    val onSuccessJoined = Transformations.map(_addRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }
}