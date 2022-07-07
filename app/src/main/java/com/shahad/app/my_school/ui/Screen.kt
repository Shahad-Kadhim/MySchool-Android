package com.shahad.app.my_school.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shahad.app.my_school.R

sealed class Screen(val route: String,@StringRes val resourceId: Int,@DrawableRes val icon: Int){

    object Home: Screen("home", R.string.home,R.drawable.ic_home)
    object Assignment: Screen("assignment", R.string.assignment,R.drawable.ic_duty)
    object Notification: Screen("notification", R.string.notification,R.drawable.ic_notifaction)
    object Profile: Screen("profile", R.string.profile,R.drawable.ic_profile)

}
