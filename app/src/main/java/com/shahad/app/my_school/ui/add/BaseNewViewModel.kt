package com.shahad.app.my_school.ui.add

import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.ui.base.BaseViewModel

abstract class BaseNewViewModel: BaseViewModel() {

    val name = MutableLiveData<String>()

    abstract fun onClickAdd()
}