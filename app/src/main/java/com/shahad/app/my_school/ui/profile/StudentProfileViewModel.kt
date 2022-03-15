package com.shahad.app.my_school.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel() , SchoolInteractionListener{

    val info = repository.getStudentInfo().asLiveData()

    val schools = repository.getStudentSchools().asLiveData()

}