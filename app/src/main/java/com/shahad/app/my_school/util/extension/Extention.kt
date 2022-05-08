package com.shahad.app.my_school.util.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.ui.add.post.PostType
import com.shahad.app.my_school.ui.home.student.HomeItem
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.EventObserver
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun View.goToFragment(navDir: NavDirections) {
    Navigation.findNavController(this).navigate(navDir)
}

fun String.toRole()=
    when(this){
        "TEACHER" -> Role.TEACHER
        "STUDENT" -> Role.STUDENT
        else -> Role.MANGER
    }

fun String.toPostType()=
    when(this){
        "LESSON" -> PostType.LESSON
        else -> PostType.DUTY
    }

fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun List<ClassM>.toHomeItems() =
    this.map { HomeItem.ClassItem(it) }

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, function:(T) ->Unit){
    this.observe(owner, EventObserver{ it ->
        function(it)
    })
}

fun View.hide(){
    this.visibility =View.GONE
}

fun View.show(){
    this.visibility =View.VISIBLE
}

suspend fun <T> MediatorLiveData<State<T>>.refresh(
    isRefresh: Boolean,
    request:  () -> Flow<State<T>>,
    authentication: MutableLiveData<State.UnAuthorization?>,
    refreshState: MutableLiveData<Boolean>,
) {
    if(isRefresh){
        with(request()){
            this@refresh.handle(this,authentication,refreshState)
        }
    }
}


suspend fun <T> MediatorLiveData<State<T>>.refresh(
    isRefresh: Boolean,
    request:  (String,String?) -> Flow<State<T>>,
    authentication: MutableLiveData<State.UnAuthorization?>,
    refreshState: MutableLiveData<Boolean>,
    firstpara: String,
    secondPara: String?
) {
    if(isRefresh){
        with(request(firstpara,secondPara)){
            this@refresh.handle(this,authentication,refreshState)
        }
    }
}

suspend fun <T> MediatorLiveData<State<T>>.handle(
    response: Flow<State<T>>,
    authentication: MutableLiveData<State.UnAuthorization?>,
    refreshState: MutableLiveData<Boolean>,
) {
    response.collect {
        if (it is State.UnAuthorization) {
            authentication.postValue(it)
        }
        if (it == State.ConnectionError || it is State.Error || it is State.Success || it == State.UnAuthorization) {
            refreshState.postValue(false)
        }
        this@handle.postValue(it)
    }
}
