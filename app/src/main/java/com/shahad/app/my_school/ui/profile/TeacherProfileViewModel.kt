package com.shahad.app.my_school.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherProfileViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), SchoolInteractionListener{

    val info = repository.getTeacherInfo().asLiveData()

    val schools = repository.getTeacherSchools().asLiveData()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    init {
        viewModelScope.launch {
            repository.refreshTeacherSchool()
        }
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }
}