package com.shahad.app.my_school.ui.manger.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchoolTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository
): SchoolBaseViewModel() {
    //TODO LATER CHANGER FUNCTION GET SCHOOLS OF TEACHER
    override val schools = repository.getTeacherSchools().asLiveData()

    private val _clickJoinSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickJoinSchoolEvent: LiveData<Event<Boolean>> = _clickJoinSchoolEvent

    override fun onClickAddSchool() {
        _clickJoinSchoolEvent.postValue(Event(true))
    }
}