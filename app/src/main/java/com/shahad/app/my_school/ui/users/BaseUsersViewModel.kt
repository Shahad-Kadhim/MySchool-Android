package com.shahad.app.my_school.ui.users

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.UserSelectedInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseUsersViewModel(
    repository: MySchoolRepository
): BaseViewModel(), UserSelectedInteractionListener {

    val schools = repository.getMangerSchool().asLiveData()

    val search = MutableLiveData<String?>(null)

    val schoolName = MutableLiveData<String?>()

    val schoolId = Transformations.map(schoolName){
        schools.value?.find { it.name == schoolName.value }?.id
    }

    abstract val users: LiveData<State<BaseResponse<List<UserSelected>>?>>

    val refreshState = MutableLiveData<Boolean>(false)

    abstract fun onClickAdd()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent



    fun MediatorLiveData<LiveData<State<BaseResponse<List<UserSelected>>?>>>.refresh(
        isRefresh: Boolean,
        request:  (String,String?) -> Flow<State<BaseResponse<List<UserSelected>>?>>,
    ) {
        if(isRefresh){
            schoolId.value?.let { school ->
                with(request(school, search.value)){
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

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    override fun onClickSelect(id: String) {
        TODO("Not yet implemented")
    }

}