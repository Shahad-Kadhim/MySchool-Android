package com.shahad.app.my_school.ui.manger.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.launch

abstract class SchoolBaseViewModel: BaseViewModel() ,
    SchoolInteractionListener {

    abstract val schools: LiveData<List<School>>
    abstract fun onClickAddSchool()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

}