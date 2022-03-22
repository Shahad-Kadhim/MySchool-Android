package com.shahad.app.my_school.ui.teacher.home

import android.util.Log
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.ClassDto
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.ClassInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), ClassInteractionListener{

    val search = MutableLiveData<String?>(null)

    val classes: LiveData<State<BaseResponse<List<ClassList>>?>> =
        Transformations.switchMap(search){ searchKey ->
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

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(classes){
            if(it is State.UnAuthorization) this.postValue(it)
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