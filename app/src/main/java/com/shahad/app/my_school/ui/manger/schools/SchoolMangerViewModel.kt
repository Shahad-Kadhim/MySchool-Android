package com.shahad.app.my_school.ui.manger.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchoolMangerViewModel @Inject constructor(
    repository: MySchoolRepository
): SchoolBaseViewModel(),
    SchoolInteractionListener
{

    override val schools = repository.getMangerSchool().asLiveData()

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(schools,::whenUnAuthorization)
    }

    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    private val _clickCreateSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateSchoolEvent: LiveData<Event<Boolean>> = _clickCreateSchoolEvent

    override fun onClickAddSchool(){
        _clickCreateSchoolEvent.postValue(Event(true))
    }
}