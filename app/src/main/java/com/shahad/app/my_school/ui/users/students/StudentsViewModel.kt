package com.shahad.app.my_school.ui.users.students

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseUsersViewModel(repository){

    private val students= MediatorLiveData<LiveData<State<BaseResponse<List<UserSelected>>?>>>().apply {
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

    override val users: LiveData<State<BaseResponse<List<UserSelected>>?>> = Transformations.switchMap(students) { it }

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    override fun onClickAdd() {
        schoolName.value?.let {
            _clickAddStudentEvent.postValue(Event(it))
        }
    }

}