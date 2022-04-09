package com.shahad.app.my_school.ui.home.student

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(), HomeStudentInteractionListener,ClassInteractionListener{

    val search = MutableLiveData<String?>(null)

    val classes = Transformations.switchMap(search){ searchKey ->
        repository.getStudentClasses(searchKey?.takeIf { it.isNotBlank() }).asLiveData()
    }

    val refreshState = MutableLiveData<Boolean>(false)

    private val _clickProfileEvent = MutableLiveData<Event<Boolean>>()
    val clickProfileEvent: LiveData<Event<Boolean>> = _clickProfileEvent

    private val _clickClassEvent = MutableLiveData<Event<Pair<String,String>>>()
    val clickClassEvent: LiveData<Event<Pair<String,String>>> = _clickClassEvent

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(classes){
//            if(it is State.UnAuthorization) this.postValue(it)
//        }
    }

    init {
        refreshClasses()
    }

    fun refreshClasses(){
        viewModelScope.launch {
            repository.refreshStudentClasses(search.value)
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