package com.shahad.app.my_school.ui.schools

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolMangerViewModel @Inject constructor(
    repository: MySchoolRepository
): SchoolBaseViewModel(),
    SchoolInteractionListener
{

    override val schools = repository.getMangerSchool().asLiveData()

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(schools,::whenUnAuthorization)
    }

    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    private val _clickCreateSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateSchoolEvent: LiveData<Event<Boolean>> = _clickCreateSchoolEvent

    init {
        viewModelScope.launch {
            repository.refreshMangerSchool()
        }
    }
    override fun onClickAddSchool(){
        _clickCreateSchoolEvent.postValue(Event(true))
    }
}