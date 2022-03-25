package com.shahad.app.my_school.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.PostDto
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel() , PostInteractionListener{

    private val _students =MutableLiveData<State<BaseResponse<List<PostDto>>?>>()
    val students: LiveData<State<BaseResponse<List<PostDto>>?>> = _students

    private val _clickCreatePostEvent = MutableLiveData<Event<String>>()
    val clickCreatePostEvent: LiveData<Event<String>> = _clickCreatePostEvent

    private val _clickPostEvent = MutableLiveData<Event<String>>()
    val clickPostEvent: LiveData<Event<String>> = _clickPostEvent

    private lateinit var classId: String

    fun getPosts(classId: String){
        this.classId = classId
        viewModelScope.launch {
            repository.getPostInClass(classId).collect {
                _students.postValue(it)
            }
        }
    }

    fun onClickCreatePost(){
        _clickCreatePostEvent.postValue(Event(classId))
    }

    override fun onClickPost(postId: String) {
        _clickPostEvent.postValue(Event(postId))
    }

}
