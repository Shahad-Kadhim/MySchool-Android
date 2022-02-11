package com.shahad.app.my_school.util.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.ui.login.UserType
import com.shahad.app.my_school.ui.register.Role

fun View.goToFragment(navDir: NavDirections) {
    Navigation.findNavController(this).navigate(navDir)
}

fun String.toRole()=
    when(this){
        "TEACHER" -> Role.TEACHER
        "STUDENT" -> Role.STUDENT
        else -> Role.MANGER
    }

fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}




