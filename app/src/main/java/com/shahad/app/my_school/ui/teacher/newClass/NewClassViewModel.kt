package com.shahad.app.my_school.ui.teacher.newClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.teacher.home.CreateClassBody
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewClassViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseViewModel() {

    val schoolName = MutableLiveData<String>()
    val className = MutableLiveData<String>()
    val stage = MutableLiveData<String>()

    private val _createRequestStatus = MutableLiveData<State<Int?>>()

    val onSuccessCreated = Transformations.map(_createRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    fun onClickCreateSchool(){
        validateInputs()?.let { requestBody ->
            viewModelScope.launch {
                repository.createClass(requestBody).collect {
                    _createRequestStatus.postValue(it)
                }
            }
        }
    }

    private fun validateInputs() =
        takeIf{
            !(schoolName.value.isNullOrBlank()) &&
            !(className.value.isNullOrBlank()) &&
            stage.value?.toIntOrNull() !=null
        }?.let{
            dataClassParser.parseToJson(
                CreateClassBody(
                    className.value!!,
                    schoolName.value!!,
                    stage.value?.toIntOrNull()
                )
            )
        }


}