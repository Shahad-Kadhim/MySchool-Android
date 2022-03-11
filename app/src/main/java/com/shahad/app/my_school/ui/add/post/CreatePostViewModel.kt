package com.shahad.app.my_school.ui.add.post

import android.util.Log
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.DataClassParser
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    val repository: MySchoolRepository,
    savedStateHandle: SavedStateHandle,
    val dataClassParser: DataClassParser
): BaseViewModel() {

    val postType = MutableLiveData<PostType>(PostType.LESSON)
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    val classId = savedStateHandle.get<String>("classId")


    private val _addRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()

    val onSuccessJoined = Transformations.map(_addRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    fun onClickPosted() {
        CreatePostBody.create(
            title.value,
            content.value,
            null,
            classId,
            postType.value?.name
        )?.let {
            viewModelScope.launch {
                repository.createPost(dataClassParser.parseToJson(it)).collect {
                    _addRequestStatus.postValue(it)
                }
            }
        }
    }
}
