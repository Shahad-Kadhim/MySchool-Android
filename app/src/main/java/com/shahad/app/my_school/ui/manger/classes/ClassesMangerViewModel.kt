package com.shahad.app.my_school.ui.manger.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.ClassInteractionListener
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassesMangerViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), ClassInteractionListener {

    val classes = repository.getMangerClasses().asLiveData()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent


    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(classes,::whenUnAuthorization)
    }

    private fun <T>whenUnAuthorization(state: State<T>){
        if(state is State.UnAuthorization) unAuthentication.postValue(state)
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

}