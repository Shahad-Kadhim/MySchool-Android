package com.shahad.app.my_school.ui.manger.home

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMangerViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(),
   SchoolInteractionListener,
   ClassInteractionListener {

    val classes = repository.getMangerClasses().asLiveData()

    val schools = repository.getMangerSchool().asLiveData()

    private val _clickCreateSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateSchoolEvent: LiveData<Event<Boolean>> = _clickCreateSchoolEvent

    private val _clickSchoolsEvent = MutableLiveData<Event<Boolean>>()
    val clickSchoolsEvent: LiveData<Event<Boolean>> = _clickSchoolsEvent

    private val _clickNotificationEvent = MutableLiveData<Event<Boolean>>()
    val clickNotificationEvent: LiveData<Event<Boolean>> = _clickNotificationEvent

    private val _clickProfileEvent = MutableLiveData<Event<Boolean>>()
    val clickProfileEvent: LiveData<Event<Boolean>> = _clickProfileEvent

    private val _clickClassesEvent = MutableLiveData<Event<Boolean>>()
    val clickClassesEvent: LiveData<Event<Boolean>> = _clickClassesEvent

    private val _clickStudentEvent = MutableLiveData<Event<Boolean>>()
    val clickStudentEvent: LiveData<Event<Boolean>> = _clickStudentEvent

    private val _clickTeachersEvent = MutableLiveData<Event<Boolean>>()
    val clickTeachersEvent: LiveData<Event<Boolean>> = _clickTeachersEvent

    private val _clickClassEvent = MutableLiveData<Event<Pair<String,String>>>()
    val clickClassEvent: LiveData<Event<Pair<String,String>>> = _clickClassEvent

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(classes,::whenUnAuthorization)
    }

    init {
        viewModelScope.launch {
            repository.refreshMangerSchool()
            repository.refreshMangerClasses()
        }
    }

    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    fun onClickCreateSchool(){
        _clickCreateSchoolEvent.postValue(Event(true))
    }

    fun onClickSchools(){
        _clickSchoolsEvent.postValue(Event(true))
    }

    fun onClickClasses(){
        _clickClassesEvent.postValue(Event(true))
    }

    fun onClickStudent(){
        _clickStudentEvent.postValue(Event(true))
    }
    fun onClickTeachers(){
        _clickTeachersEvent.postValue(Event(true))
    }

    fun onclickNotification(){
        _clickNotificationEvent.postValue(Event(true))
    }
    fun onclickProfile(){
        _clickProfileEvent.postValue(Event(true))
    }

    override fun onClickClass(classId: String, className: String) {
        _clickClassEvent.postValue(Event(Pair(classId,className)))
    }
}