package com.shahad.app.my_school.ui.home.student

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(), HomeStudentInteractionListener,ClassInteractionListener{

    val classes = repository.getStudentClasses().asLiveData()

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
//        addSource(classes){
//            if(it is State.UnAuthorization) this.postValue(it)
//        }
    }

    init {
        viewModelScope.launch {
            repository.refreshStudentClasses()
        }
    }
    override fun onClickClass(classId: String, className: String) {

    }
}