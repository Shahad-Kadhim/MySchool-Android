package com.shahad.app.my_school.ui.home.manger

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMangerViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(),
    SchoolInteractionListener,
    ClassInteractionListener, HomeMangerInteractionListener{

    val classes = repository.getMangerClasses().asLiveData()

    val schools = repository.getMangerSchool().asLiveData()

    val refreshState = MutableLiveData<Boolean>(false)

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

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _unAuthentication = MutableLiveData<State.UnAuthorization?>()
    val unAuthentication: LiveData<State.UnAuthorization?> = _unAuthentication

    init {
        refreshClasses()
        refreshSchools()
    }

    fun refreshSchools(){
        viewModelScope.launch {
            repository.refreshMangerSchool().collect {
                if(it is State.UnAuthorization)
                    _unAuthentication.postValue(it)

            }
        }
    }
    fun refreshClasses(){
        viewModelScope.launch {
            repository.refreshMangerClasses().collect {
                if(it is State.UnAuthorization){
                    _unAuthentication.postValue(it)
                    refreshState.postValue(false)
                }
                if(it == State.ConnectionError || it is State.Error || it is State.Success || it == State.UnAuthorization){
                    refreshState.postValue(false)
                }
            }
        }
    }

    fun onClickCreateSchool(){
        _clickCreateSchoolEvent.postValue(Event(true))
    }

    override fun onClickSchools(){
        _clickSchoolsEvent.postValue(Event(true))
    }

    override fun onClickClasses(){
        _clickClassesEvent.postValue(Event(true))
    }

    override fun onClickStudent(){
        _clickStudentEvent.postValue(Event(true))
    }
    override fun onClickTeachers(){
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