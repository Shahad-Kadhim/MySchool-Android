package com.shahad.app.my_school.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangerProfileViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel() , SchoolInteractionListener{

    val info = repository.getMangerInfo().asLiveData()

    val schools = repository.getMangerSchool().asLiveData()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    val onSwipe = fun(id: Int){
        Log.i("TAG","THIS ITEM IS SWIPED : $id")
    }

    init {
        viewModelScope.launch {
            repository.refreshMangerSchool()
        }
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

}