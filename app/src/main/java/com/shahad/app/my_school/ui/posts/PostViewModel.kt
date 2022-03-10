package com.shahad.app.my_school.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseViewModel(){
    private val _clickCreatePostEvent = MutableLiveData<Event<String>>()
    val clickCreatePostEvent: LiveData<Event<String>> = _clickCreatePostEvent

    fun onClickCreatePost(classId: String){
        _clickCreatePostEvent.postValue(Event(classId))
    }

}
