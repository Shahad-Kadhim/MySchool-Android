package com.shahad.app.my_school.ui.users.teachers

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
class TeachersViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val dataClassParser: DataClassParser
): BaseUsersViewModel(repository){

    private val teachers= MediatorLiveData<LiveData<State<BaseResponse<List<UserSelected>>?>>>().apply {
        addSource(schoolId){
            it?.let { schoolId ->
                this.postValue(repository.getSchoolTeachers(schoolId, search.value?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(search){ searchKey ->
            schoolId.value?.let { schoolId ->
                this.postValue(repository.getSchoolTeachers(schoolId ,searchKey?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(refreshState) {
            this.refresh(it,repository::getSchoolTeachers)
        }
    }

    override val users: LiveData<State<BaseResponse<List<UserSelected>>?>> = Transformations.switchMap(teachers) { it }

    private val _clickAddTeacherEvent = MutableLiveData<Event<String>>()
    val clickAddTeacherEvent: LiveData<Event<String>> = _clickAddTeacherEvent

    override fun onClickAdd() {
        schoolId.value?.let { schoolId ->
            _clickAddTeacherEvent.postValue(Event(schoolId))
        }
    }

    override fun onClickDelete() {
        teachers.value?.value?.toData()?.data
            ?.filter { it.isSelected }
            ?.takeIf { it.isNotEmpty() }
            ?.map{it.id}
            ?.let {
                getMemberClassBody(it)?.let { body ->
                    viewModelScope.launch {
                        repository.removeTeacherFromSchool(
                            dataClassParser.parseToJson(body)
                        ).collect {
                            if(it is State.Success){
                                refreshState.postValue(true)
                            }
                        }
                    }

                }
            }
    }

    override fun onClickSelect(id: String) {
        val list = teachers.value?.value?.toData()?.data?.map {
            UserSelected(
                it.id,
                it.name,
                if(it.id == id) !it.isSelected else it.isSelected
            )
        }
        teachers.postValue(MutableLiveData(State.Success(BaseResponse(200,list ?: emptyList()))))
    }
}