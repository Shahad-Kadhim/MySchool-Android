package com.shahad.app.my_school.ui.identity

import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.local.DataStorePreferences
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityViewModel @Inject constructor(
    repository: MySchoolRepository,
    private val dataStorePreferences: DataStorePreferences
): BaseViewModel(){

    fun storeToken(token: String){
        viewModelScope.launch {
            dataStorePreferences.writeTokenPre(token)
        }
    }

    fun storeRole(role: String){
        viewModelScope.launch {
            dataStorePreferences.writeRolePre(role)
        }
    }

}