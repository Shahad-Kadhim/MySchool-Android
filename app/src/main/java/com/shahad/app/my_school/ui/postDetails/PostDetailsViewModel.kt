package com.shahad.app.my_school.ui.postDetails

import androidx.lifecycle.*
import com.example.models.PostDetailsDto
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() , CommentInteractionListener{

    val refreshState = MutableLiveData<Boolean>(false)

    val postId =savedStateHandle.get<String>("postId") ?: ""

    val postDetails =
        MediatorLiveData<State<BaseResponse<PostDetailsDto>?>?>().apply {
            getDetails(this)
            addSource(refreshState){
                refreshPost(it,this)
            }
        }

    val content = MutableLiveData<String?>()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    private fun getDetails(details: MediatorLiveData<State<BaseResponse<PostDetailsDto>?>?>) {
        viewModelScope.launch {
            repository.getPostDetails(postId).collect {
                details.postValue(it)
            }
        }
    }
    fun onClickSend(){
        content.value.takeUnless { it.isNullOrBlank() }?.let {
            viewModelScope.launch{
                repository.createComment(postId, it).collect {
                   refreshPost(true, postDetails)
                }
            }
            clearContent()
        }

    }

    private fun clearContent() {
        content.postValue(null)
    }

    private fun refreshPost(
        state: Boolean,
        details: MediatorLiveData<State<BaseResponse<PostDetailsDto>?>?>
    ){
        takeIf { state }?.let {
            viewModelScope.launch {
                repository.getPostDetails(postId).collect {
                    when(it){
                        is State.Error -> {
                            refreshState.postValue(false)
                        }
                        is State.Success -> {
                            details.postValue(it)
                            refreshState.postValue(false)
                        }
                    }
                }
            }
        }
    }

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }


}