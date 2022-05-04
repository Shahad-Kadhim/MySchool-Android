package com.shahad.app.my_school.ui.notification

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.NotificationDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), NotificationInteractionListener{

    val refreshState = MutableLiveData<Boolean>(false)

    val notification =MediatorLiveData<LiveData<State<BaseResponse<List<NotificationDto>>?>>>().apply {
       this.postValue(repository.getNotification().asLiveData())
        addSource(refreshState){
            this.refresh(it,repository::getNotification)
        }
    }

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }


    fun MediatorLiveData<LiveData<State<BaseResponse<List<NotificationDto>>?>>>.refresh(
        isRefresh: Boolean,
        request:  () -> Flow<State<BaseResponse<List<NotificationDto>>?>>,
    ) {
        if(isRefresh){
            with(request()){
                viewModelScope.launch {
                    this@with.collect {
                        if(it == State.ConnectionError || it is State.Error || it is State.Success || it == State.UnAuthorization){
                            refreshState.postValue(false)
                        }
                    }
                }
                this@refresh.postValue(this.asLiveData())
            }
        }
    }

}