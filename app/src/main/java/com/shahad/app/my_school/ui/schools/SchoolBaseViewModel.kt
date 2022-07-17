package com.shahad.app.my_school.ui.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import kotlinx.coroutines.flow.MutableStateFlow

abstract class SchoolBaseViewModel: BaseViewModel() ,
    SchoolInteractionListener {

    abstract val schools: LiveData<List<School>>
    abstract fun onClickAddSchool()

    val refreshState = MutableStateFlow<Boolean>(false)

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

}