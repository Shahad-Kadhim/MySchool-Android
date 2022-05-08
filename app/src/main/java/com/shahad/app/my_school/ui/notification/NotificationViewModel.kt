package com.shahad.app.my_school.ui.notification

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.NotificationDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import com.shahad.app.my_school.util.extension.handle
import com.shahad.app.my_school.util.extension.refresh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(), NotificationInteractionListener{

    val refreshState = MutableLiveData<Boolean>(false)


    private val _unAuthentication = MutableLiveData<State.UnAuthorization?>()
    val unAuthentication: LiveData<State.UnAuthorization?> = _unAuthentication

    val notification =MediatorLiveData<State<BaseResponse<List<NotificationDto>>?>>().apply {
        viewModelScope.launch {
            handle(repository.getNotification(), _unAuthentication, refreshState)
        }
        addSource(refreshState){
            viewModelScope.launch {
                refresh(it,repository::getNotification,_unAuthentication,refreshState)
            }
        }

    }

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

}