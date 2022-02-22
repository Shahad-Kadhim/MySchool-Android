package com.shahad.app.my_school.ui.users.studnets

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.StudentDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.ui.users.UserInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseUsersViewModel(repository){

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    override val users: LiveData<State<BaseResponse<List<StudentDto>>?>> = Transformations.switchMap(schoolName){
        it?.let {
            repository.getSchoolStudents(it).asLiveData()
        }
    }

    override fun onClickAdd() {
        schoolName.value?.let {
            _clickAddStudentEvent.postValue(Event(it))
        }
    }
}