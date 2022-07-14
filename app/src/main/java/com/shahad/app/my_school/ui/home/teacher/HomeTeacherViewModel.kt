package com.shahad.app.my_school.ui.home.teacher

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(), ClassInteractionListener {

    val search = MutableLiveData<String?>(null)

    val refreshState = MutableStateFlow(false)

    val classes = Transformations.switchMap(search){ searchKey ->
            repository.getTeacherClasses(searchKey?.takeIf { it.isNotBlank() }).asLiveData()
        }

    private val _clickCreateClassEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateClassEvent: LiveData<Event<Boolean>> = _clickCreateClassEvent

    private val _clickSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickSchoolEvent: LiveData<Event<Boolean>> = _clickSchoolEvent

    private val _clickClassEvent = MutableLiveData<Event<Pair<String,String>>>()
    val clickClassEvent: LiveData<Event<Pair<String,String>>> = _clickClassEvent

    private val _clickProfileEvent = MutableLiveData<Event<Boolean>>()
    val clickProfileEvent: LiveData<Event<Boolean>> = _clickProfileEvent

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _unAuthentication = MutableLiveData<State.UnAuthorization?>()
    val unAuthentication: LiveData<State.UnAuthorization?> = _unAuthentication


    init {
        refreshClasses()
    }

    fun refreshClasses(){
        viewModelScope.launch {
            repository.refreshTeacherClasses(search.value).collect {
                when(it){
                    State.ConnectionError,is State.Error -> _message.postValue("no connection")
                    is State.Success -> _message.postValue("Update")
                    State.UnAuthorization -> _unAuthentication.postValue(State.UnAuthorization)
                }
            }
            refreshState.emit(false)
        }
    }


    fun onClickCreateClass(){
        _clickCreateClassEvent.postValue(Event(true))
    }

    fun onClickSchools(){
        _clickSchoolEvent.postValue(Event(true))
    }

    override fun onClickClass(classId: String, className: String) {
        _clickClassEvent.postValue(Event(Pair(classId,className)))
    }

    fun onclickProfile(){
        _clickProfileEvent.postValue(Event(true))
    }

}