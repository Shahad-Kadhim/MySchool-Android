package com.shahad.app.my_school.ui.users.studnets

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.UserDto
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseUsersViewModel(repository){

    private val students= MediatorLiveData<LiveData<State<BaseResponse<List<UserDto>>?>>>().apply {
        addSource(schoolName){
            it?.let { schoolName ->
                this.postValue(repository.getSchoolStudents(schoolName, search.value?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(search){ searchKey ->
            schoolName.value?.let { schoolName ->
                this.postValue(repository.getSchoolStudents(schoolName, searchKey?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
    }

    override val users: LiveData<State<BaseResponse<List<UserDto>>?>> = Transformations.switchMap(students) { it }

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    override fun onClickAdd() {
        schoolName.value?.let {
            _clickAddStudentEvent.postValue(Event(it))
        }
    }
}