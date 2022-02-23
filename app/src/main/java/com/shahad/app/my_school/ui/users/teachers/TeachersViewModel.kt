package com.shahad.app.my_school.ui.users.teachers

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
class TeachersViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseUsersViewModel(repository){

    private val _clickAddTeacherEvent = MutableLiveData<Event<String>>()
    val clickAddTeacherEvent: LiveData<Event<String>> = _clickAddTeacherEvent

    override val users: LiveData<State<BaseResponse<List<UserDto>>?>> = Transformations.switchMap(schoolName){
        it?.let {
            repository.getSchoolTeachers(it).asLiveData()
        }
    }

    override fun onClickAdd() {
        schoolName.value?.let {
            _clickAddTeacherEvent.postValue(Event(it))
        }
    }
}