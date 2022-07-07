package com.shahad.app.my_school.ui.main

import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.local.DataStorePreferences
import com.shahad.app.my_school.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: MySchoolRepository,
    private val dataStorePreferences: DataStorePreferences
): BaseViewModel(){
    val role = dataStorePreferences.readRolePre()
}