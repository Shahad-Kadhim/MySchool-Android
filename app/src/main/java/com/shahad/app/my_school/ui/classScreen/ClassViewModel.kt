package com.shahad.app.my_school.ui.classScreen

import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel()