package com.shahad.app.my_school.ui.manger.newSchool

import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.ui.base.BaseViewModel

abstract class BaseNewSchoolViewModel: BaseViewModel() {

    val schoolName = MutableLiveData<String>()

    abstract fun onClickAddSchool()
}