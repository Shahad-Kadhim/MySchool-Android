package com.shahad.app.my_school.ui.duty

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.AssignmentDto
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssignmentStudentViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseAssignmentViewModel(), AssignmentInteractionListener{

    override val assignments =
        MediatorLiveData<LiveData<State<BaseResponse<List<AssignmentDto>>?>>>().apply {
            this.postValue(repository.getAssignmentForStudent().asLiveData())
            addSource(refreshState) {
                this.refresh(it,repository::getAssignmentForStudent)
            }
        }

}