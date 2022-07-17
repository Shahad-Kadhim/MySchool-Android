package com.shahad.app.my_school.ui.users.students

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import com.shahad.app.my_school.util.extension.handle
import com.shahad.app.my_school.util.extension.refresh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseUsersViewModel(repository){

     val students= MediatorLiveData<State<BaseResponse<List<UserSelected>>?>>().apply {
        addSource(schoolId){
            it?.let { schoolId ->
                viewModelScope.launch {
                    handle(repository.getSchoolStudents(schoolId, search.value?.takeIf { it.isNotBlank() }),_unAuthentication,refreshState)
                }
            }
        }
        addSource(search){ searchKey ->
            schoolId.value?.let { schoolId ->
                viewModelScope.launch {
                    handle(repository.getSchoolStudents(schoolId, searchKey?.takeIf { it.isNotBlank() }),_unAuthentication,refreshState)
                }
            }
        }
        addSource(refreshState) {
            viewModelScope.launch {
                this@apply.refresh(it,repository::getSchoolStudents,_unAuthentication,refreshState,schoolId.value ?: "",search.value)
            }
        }

    }


    override val users: LiveData<State<BaseResponse<List<UserSelected>>?>> = students

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    override fun onClickAdd() {
        schoolId.value?.let { schoolId ->
            _clickAddStudentEvent.postValue(Event(schoolId))
        }
    }

    override fun onClickDelete() {
        students.value?.toData()?.data
            ?.filter { it.isSelected }
            ?.takeIf { it.isNotEmpty() }
            ?.map{it.id}
            ?.let {
                getMemberClassBody(it)?.let { body ->
                    viewModelScope.launch {
                        repository.removeStudentFromSchool(
                            dataClassParser.parseToJson(body)
                        ).collect {
                            if(it is State.Success){
                                refreshState.postValue(true)
                            }
                        }
                    }

                }
            }    }

    override fun onClickSelect(id: String) {
        val list = students.value?.toData()?.data?.map {
            UserSelected(
                it.id,
                it.name,
                if(it.id == id) !it.isSelected else it.isSelected
            )
        }
        students.postValue(State.Success(BaseResponse(200,list ?: emptyList())))

    }

}