package com.shahad.app.my_school.ui.add.post

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    val repository: MySchoolRepository,
    savedStateHandle: SavedStateHandle,
    val dataClassParser: DataClassParser
): BaseViewModel() {

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    val classId = savedStateHandle.get<String>("classId")

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    fun onClickPosted(){
    }

}
