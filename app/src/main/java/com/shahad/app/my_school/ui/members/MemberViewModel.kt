package com.shahad.app.my_school.ui.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    repository: MySchoolRepository,
): BaseViewModel(){


    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    lateinit var classId: String

    fun getMembers(classId: String){
        this.classId = classId
    }

    fun onClickAddStudent(){
        _clickAddStudentEvent.postValue(Event(classId))
    }

}