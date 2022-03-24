package com.shahad.app.my_school.ui.manger.classes

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.ClassInteractionListener
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesMangerViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), ClassInteractionListener {

    val classes = repository.getMangerClasses().asLiveData()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent


    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(classes,::whenUnAuthorization)
    }

    init {
        viewModelScope.launch {
            repository.refreshMangerClasses()
        }
    }
    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    override fun onClickClass(classId: String,className: String) {
        TODO("Not yet implemented")
    }

}