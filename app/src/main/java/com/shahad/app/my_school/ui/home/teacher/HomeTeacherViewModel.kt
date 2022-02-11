package com.shahad.app.my_school.ui.home.teacher

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(){

    val classes = repository.getTeacherClasses().asLiveData()

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(classes){
            if(it is State.UnAuthorization) this.postValue(it)
        }
    }
}