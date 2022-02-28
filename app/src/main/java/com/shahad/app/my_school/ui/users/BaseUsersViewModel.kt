package com.shahad.app.my_school.ui.users

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.StudentDto
import com.shahad.app.my_school.data.remote.response.UserDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.users.UserInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class BaseUsersViewModel(
    repository: MySchoolRepository
): BaseViewModel(), UserInteractionListener {

    val schools = repository.getMangerSchool().asLiveData()

    val schoolName = MutableLiveData<String?>()

    abstract val users: LiveData<State<BaseResponse<List<UserDto>>?>>

    abstract fun onClickAdd()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }
}