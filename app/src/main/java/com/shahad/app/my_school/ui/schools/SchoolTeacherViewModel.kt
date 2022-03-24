package com.shahad.app.my_school.ui.schools

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository
): SchoolBaseViewModel() {
    //TODO LATER CHANGER FUNCTION GET SCHOOLS OF TEACHER
    override val schools = repository.getTeacherSchools().asLiveData()

    init {
        viewModelScope.launch {
            repository.refreshTeacherSchool()
        }
    }
    override fun onClickAddSchool() {
    }
}