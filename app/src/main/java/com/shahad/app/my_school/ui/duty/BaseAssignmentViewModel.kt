package com.shahad.app.my_school.ui.duty

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.AssignmentDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseAssignmentViewModel: BaseViewModel(), AssignmentInteractionListener{

    val refreshState = MutableLiveData<Boolean>(false)

    abstract val assignments: MediatorLiveData<LiveData<State<BaseResponse<List<AssignmentDto>>?>>>

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    private val _clickDutyEvent = MutableLiveData<Event<String>>()
    val clickDutyEvent: LiveData<Event<String>> = _clickDutyEvent


    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }


    fun MediatorLiveData<LiveData<State<BaseResponse<List<AssignmentDto>>?>>>.refresh(
        isRefresh: Boolean,
        request:  () -> Flow<State<BaseResponse<List<AssignmentDto>>?>>,
    ) {
        if(isRefresh){
            with(request()){
                viewModelScope.launch {
                    this@with.collect {
                        if(it is State.Success){
                            refreshState.postValue(false)
                        }
                    }
                }
                this@refresh.postValue(this.asLiveData())
            }
        }
    }

    override fun onClickDuty(dutyId: String) {
        _clickDutyEvent.postValue(Event(dutyId))
    }

}