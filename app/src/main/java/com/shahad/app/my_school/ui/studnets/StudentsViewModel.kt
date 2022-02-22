package com.shahad.app.my_school.ui.studnets

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.StudentDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), UserInteractionListener{

    val schools = repository.getMangerSchool().asLiveData()

    val schoolName = MutableLiveData<String?>()

    val students: LiveData<State<BaseResponse<List<StudentDto>>?>> = Transformations.switchMap(schoolName){
        it?.let {
            repository.getSchoolStudents(it).asLiveData()
        }
    }

}