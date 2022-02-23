package com.shahad.app.my_school.ui.classScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassScreenViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(){
    val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent
    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }
}