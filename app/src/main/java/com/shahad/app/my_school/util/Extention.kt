package com.shahad.app.my_school.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.ui.login.UserType

fun View.goToFragment(navDir: NavDirections) {
    Navigation.findNavController(this).navigate(navDir)
}

fun String.toUserType()=
    when(this){
        "TEACHER" -> UserType.TEACHER
        "STUDENT" -> UserType.STUDENT
        else -> UserType.MANGER
    }
