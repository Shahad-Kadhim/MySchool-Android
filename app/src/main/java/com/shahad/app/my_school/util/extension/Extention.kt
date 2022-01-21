package com.shahad.app.my_school.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.ui.login.UserType

fun View.goToFragment(navDir: NavDirections) {
    Navigation.findNavController(this).navigate(navDir)
}

fun String.toUserType()=
    when(this){
        "Teacher" -> UserType.TEACHER
        "Student" -> UserType.STUDENT
        else -> UserType.MANGER
    }

fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}


