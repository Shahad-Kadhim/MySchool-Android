package com.shahad.app.my_school.ui.add.student

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionStudentsViewModel @Inject constructor(
    val repository: MySchoolRepository,
    savedStateHandle: SavedStateHandle,
    ): BaseViewModel() , StudentsSelectedInteractionListener {

    private val _students = MutableLiveData<State<BaseResponse<List<UserSelected>>?>>()
    val students: LiveData<State<BaseResponse<List<UserSelected>>?>> = _students

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    init {
        savedStateHandle.get<String>("classId")?.let {
            viewModelScope.launch {
                repository.getStudentsNotInClass(it).collect {
                    _students.postValue(it)
                }
            }
        }
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    override fun onClickSelect(id: String) {
        val list = _students.value?.toData()?.data?.map {
            UserSelected(
                it.id,
                it.name,
                if(it.id == id) !it.isSelected else it.isSelected
            )
        }
        _students.postValue(State.Success(BaseResponse(200,list ?: emptyList())))

    }

}