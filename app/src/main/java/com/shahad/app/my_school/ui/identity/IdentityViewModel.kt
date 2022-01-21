package com.shahad.app.my_school.ui.identity

import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.DataStorePreferences
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
    private val token = dataStorePreferences.readTokenPre()

    val isAuth = token.map {
        it?.let { true }
    }

    fun storeToken(token: String){
        viewModelScope.launch {
            dataStorePreferences.writeTokenPre(token)
        }
    }

}