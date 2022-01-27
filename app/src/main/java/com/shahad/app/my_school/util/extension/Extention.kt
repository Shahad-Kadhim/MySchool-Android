package com.shahad.app.my_school.util.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.ui.identity.IdentityActivity
import com.shahad.app.my_school.ui.login.UserType
import okhttp3.Response

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

fun Response.checkAuthentication(context: Context): Response{
    takeIf{ code == 401 }?.let {
        with(context) {
            startActivity(Intent(this, IdentityActivity::class.java))
            (this as Activity).finish()
        }
    }
    return this
}



