package com.shahad.app.my_school.ui.manger.schools

import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchoolTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository
): SchoolBaseViewModel() {
    //TODO LATER CHANGER FUNCTION GET SCHOOLS OF TEACHER
    override val schools = repository.getTeacherSchools().asLiveData()

    override fun onClickAddSchool() {
    }
}