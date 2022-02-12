package com.shahad.app.my_school.ui.student.home

import androidx.lifecycle.MediatorLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(){

   // val classes = repository.getTeacherClasses().asLiveData()

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(classes){
//            if(it is State.UnAuthorization) this.postValue(it)
//        }
    }
}