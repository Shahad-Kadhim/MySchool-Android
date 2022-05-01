package com.shahad.app.my_school.ui.home.student

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(), HomeStudentInteractionListener,ClassInteractionListener{

    val search = MutableLiveData<String?>(null)

    //TODO LATER SEARCH NOT WORKING :(
    val classes = Transformations.switchMap(search){ searchKey ->
        repository.getStudentClasses(searchKey?.takeIf { it.isNotBlank() }).asLiveData()
    }

    val refreshState = MutableLiveData<Boolean>(false)

    private val _clickProfileEvent = MutableLiveData<Event<Boolean>>()
    val clickProfileEvent: LiveData<Event<Boolean>> = _clickProfileEvent

    private val _clickClassEvent = MutableLiveData<Event<Pair<String,String>>>()
    val clickClassEvent: LiveData<Event<Pair<String,String>>> = _clickClassEvent

    private val _unAuthentication = MutableLiveData<State.UnAuthorization>()
    val unAuthentication: LiveData<State.UnAuthorization> = _unAuthentication
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        refreshClasses()
    }

    fun refreshClasses(){
        viewModelScope.launch {
            repository.refreshStudentClasses(search.value).collect {
                when(it){
                    State.ConnectionError,is State.Error -> _message.postValue("no connection")
                    is State.Success -> _message.postValue("Update")
                    State.UnAuthorization -> _unAuthentication.postValue(State.UnAuthorization)
                }
            }
            refreshState.postValue(false)
        }
    }

    fun onclickProfile(){
        _clickProfileEvent.postValue(Event(true))
    }

    override fun onClickClass(classId: String, className: String) {
        _clickClassEvent.postValue(Event(Pair(classId,className)))
    }
}