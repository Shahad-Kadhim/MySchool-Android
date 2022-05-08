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

    val refreshState = MutableLiveData<Boolean>(false)

    private val _posts =MutableLiveData<State<BaseResponse<List<PostDto>>?>>()
    val posts: LiveData<State<BaseResponse<List<PostDto>>?>> = _posts

    private val _clickCreatePostEvent = MutableLiveData<Event<String>>()
    val clickCreatePostEvent: LiveData<Event<String>> = _clickCreatePostEvent

    private val _clickLessonEvent = MutableLiveData<Event<String>>()
    val clickLessonEvent: LiveData<Event<String>> = _clickLessonEvent

    private val _clickDutyEvent = MutableLiveData<Event<String>>()
    val clickDutyEvent: LiveData<Event<String>> = _clickDutyEvent

    private lateinit var classId: String

    private val _unAuthentication = MutableLiveData<State.UnAuthorization?>()
    val unAuthentication: LiveData<State.UnAuthorization?> = _unAuthentication

    fun getPosts(classId: String){
        this.classId = classId
        viewModelScope.launch {
            repository.getPostInClass(classId).collect {
                if (it is State.UnAuthorization) {
                    _unAuthentication.postValue(it)
                    refreshState.postValue(false)
                }
                if (it == State.ConnectionError || it is State.Error || it is State.Success || it == State.UnAuthorization) {
                    refreshState.postValue(false)
                }
                _posts.postValue(it)
            }
        }
    }

    fun onClickCreatePost(){
        _clickCreatePostEvent.postValue(Event(classId))
    }

    override fun onClickLesson(lessonId: String) {
        _clickLessonEvent.postValue(Event(lessonId))
    }

    override fun onClickDuty(dutyId: String) {
        _clickDutyEvent.postValue(Event(dutyId))
    }

}
