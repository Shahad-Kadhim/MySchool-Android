package com.shahad.app.my_school.ui.postDetails

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle
): BasePostViewModel(repository, savedStateHandle){

    private val _clickSolutionsEvent = MutableLiveData<Event<String>>()
    val clickSolutionEvent: LiveData<Event<String>> = _clickSolutionsEvent

    fun onClickSolution(){
        _clickSolutionsEvent.postValue(Event(postId))
    }
}