package com.shahad.app.my_school.ui.solution

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.DutySubmit
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionViewModel @Inject constructor(
   private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() , SolutionInteractionListener{
    private val dutyId = savedStateHandle.get<String>("dutyId") ?: ""

    val refreshState = MutableLiveData<Boolean>(false)

    val solutions =
        MediatorLiveData<LiveData<State<BaseResponse<List<DutySubmit>>?>>>().apply {
            this.postValue(repository.getSolutionsForDuty(savedStateHandle.get<String>("dutyId") ?: "").asLiveData())
            addSource(refreshState) {
                this.refresh(it,repository::getSolutionsForDuty)
            }
        }


    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent


    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    fun MediatorLiveData<LiveData<State<BaseResponse<List<DutySubmit>>?>>>.refresh(
        isRefresh: Boolean,
        request:  (String) -> Flow<State<BaseResponse<List<DutySubmit>>?>>,
    ) {
        if(isRefresh){
            with(request(dutyId)){
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
}