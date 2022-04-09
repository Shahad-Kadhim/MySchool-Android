package com.shahad.app.my_school.ui.postDetails

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DutyDetailsViewModel @Inject constructor(
    private val repository: MySchoolRepository,
    private val savedStateHandle: SavedStateHandle
): BasePostViewModel(repository, savedStateHandle){


    val imagePost= MutableLiveData<Bitmap?>()
    val file= MutableLiveData<File?>()

    private val _clickUploadImageEvent =MutableLiveData<Event<Boolean>>()
    val clickUploadImageEvent: LiveData<Event<Boolean>> = _clickUploadImageEvent

    private val _addRequestStatus = MutableLiveData<State<BaseResponse<String>?>>()
    val onSuccessJoined = Transformations.map(_addRequestStatus){
        if(it is State.Success) Event(true) else Event(false)
    }


    fun onClickSubmit() {
        viewModelScope.launch {
//                repository.createPost(
//                    getJsonRequestBody(it),
//                    getImage()
//                ).collect {
//                    Log.i("TAG",it.toString())
//                    _addRequestStatus.postValue(it)
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