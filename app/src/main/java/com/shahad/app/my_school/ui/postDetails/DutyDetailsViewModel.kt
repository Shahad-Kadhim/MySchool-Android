package com.shahad.app.my_school.ui.postDetails

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DutyDetailsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle,
): BasePostViewModel(repository, savedStateHandle){

    val solution = repository.getSolution(postId).asLiveData()

    val imagePost= MutableLiveData<Bitmap?>()
    val file= MutableLiveData<File?>()

    private val _clickUploadImageEvent =MutableLiveData<Event<Boolean>>()
    val clickUploadImageEvent: LiveData<Event<Boolean>> = _clickUploadImageEvent

    private val _addRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()

    fun onClickSubmit() {
        viewModelScope.launch {
            getImage()?.let { solution ->
                repository.addSolution(
                    postId,
                    solution
                ).collect {
                    Log.i("TAG POSTTT", it.toString())
                    _addRequestStatus.postValue(it)
                }
            }
        }
    }

    private fun getImage()=
        file.value
            ?.asRequestBody("image/png".toMediaTypeOrNull())
            ?.let {
                MultipartBody.Part
                    .createFormData(
                        "image",
                        this.file.value?.name,
                        it
                    )
            }

    fun onClickRemoveImage(){
        imagePost.postValue(null)
        file.postValue(null)
    }

    fun onClickUploadImage(){
        _clickUploadImageEvent.postValue(Event(true))
    }

}