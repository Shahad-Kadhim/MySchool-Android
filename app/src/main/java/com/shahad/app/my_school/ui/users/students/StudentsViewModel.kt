package com.shahad.app.my_school.ui.users.students

import android.util.Log
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseUsersViewModel(repository){

    private val students= MediatorLiveData<LiveData<State<BaseResponse<List<UserSelected>>?>>>().apply {
        addSource(schoolId){
            it?.let { schoolId ->
                this.postValue(repository.getSchoolStudents(schoolId, search.value?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(search){ searchKey ->
            schoolId.value?.let { schoolId ->
                this.postValue(repository.getSchoolStudents(schoolId, searchKey?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(refreshState) {
            this.refresh(it,repository::getSchoolStudents)
        }

    }


    override val users: LiveData<State<BaseResponse<List<UserSelected>>?>> = Transformations.switchMap(students) { it }

    private val _clickAddStudentEvent = MutableLiveData<Event<String>>()
    val clickAddStudentEvent: LiveData<Event<String>> = _clickAddStudentEvent

    override fun onClickAdd() {
        schoolId.value?.let { schoolId ->
            _clickAddStudentEvent.postValue(Event(schoolId))
        }
    }

    override fun onClickDelete() {
        students.value?.value?.toData()?.data
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
        val list = students.value?.value?.toData()?.data?.map {
            UserSelected(
                it.id,
                it.name,
                if(it.id == id) !it.isSelected else it.isSelected
            )
        }
        students.postValue(MutableLiveData(State.Success(BaseResponse(200,list ?: emptyList()))))

    }

}