package com.shahad.app.my_school.ui.postDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel(){

    val postDetails = repository.getPostDetails(savedStateHandle.get<String>("postId") ?: "").asLiveData()

}