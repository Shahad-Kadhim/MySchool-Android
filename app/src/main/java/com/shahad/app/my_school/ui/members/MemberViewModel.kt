package com.shahad.app.my_school.ui.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.UserSelectedInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    val repository: MySchoolRepository,
): BaseViewModel(), UserSelectedInteractionListener{

    val refreshState = MutableLiveData<Boolean>(false)

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    lateinit var classId: String

    val students =MutableLiveData<State<BaseResponse<List<UserSelected>>?>>()

    private val _unAuthentication = MutableLiveData<State.UnAuthorization?>()
    val unAuthentication: LiveData<State.UnAuthorization?> = _unAuthentication

    fun getMembers(classId: String){
        this.classId = classId
        viewModelScope.launch {
            repository.getMemberClass(classId).collect {
                if (it is State.UnAuthorization) {
                    _unAuthentication.postValue(it)
                    refreshState.postValue(false)
                }
                if (it == State.ConnectionError || it is State.Error || it is State.Success || it == State.UnAuthorization) {
                    refreshState.postValue(false)
                }
                students.postValue(it)
            }
        }
    }

    fun onClickAddStudent(){
        _clickAddStudentEvent.postValue(Event(classId))
    }

    override fun onClickSelect(id: String) {
   //     TODO("Not yet implemented")
    }

}