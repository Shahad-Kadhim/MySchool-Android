package com.shahad.app.my_school.ui.manger.newSchool

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewSchoolViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel() {

    val schoolName = MutableLiveData<String>()

    private val _createRequestStatus = MutableLiveData<State<Int?>>()
    val createRequestStatus: LiveData<State<Int?>> = _createRequestStatus

    val onSuccessCreated = Transformations.map(createRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    fun onClickCreateSchool(){
        schoolName.value?.takeIf { it.isNotBlank() }?.let {
            viewModelScope.launch {
                repository.createSchool(it).collect {
                    _createRequestStatus.postValue(it)
                }
            }
        }
    }

}