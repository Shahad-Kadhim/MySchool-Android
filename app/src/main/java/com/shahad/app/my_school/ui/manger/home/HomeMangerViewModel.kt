package com.shahad.app.my_school.ui.manger.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMangerViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(),
   SchoolInteractionListener,
   ClassInteractionListener {

    val classes = repository.getMangerClasses().asLiveData()

    val schools = repository.getMangerSchool().asLiveData()

    private val _clickCreateSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateSchoolEvent: LiveData<Event<Boolean>> = _clickCreateSchoolEvent

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(schools,::whenUnAuthorization)
        addSource(classes,::whenUnAuthorization)
    }

    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    fun onClickCreateSchool(){
        _clickCreateSchoolEvent.postValue(Event(true))
    }
}