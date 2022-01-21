package com.shahad.app.my_school.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.shahad.app.my_school.dataStore
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

suspend fun <T> Context.writeTo(key: Preferences.Key<T>, value: T) {
    dataStore.edit { settings ->
        settings[key] = value
    }
}
