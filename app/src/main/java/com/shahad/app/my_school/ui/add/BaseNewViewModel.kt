package com.shahad.app.my_school.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event

abstract class BaseNewViewModel: BaseViewModel() {

    val name = MutableLiveData<String>()

    abstract fun onClickAdd()
    abstract val onSuccessRequest: LiveData<Event<Boolean>>
}