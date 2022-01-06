package com.shahad.app.my_school.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun View.goToFragment(navDir: NavDirections) {
    Navigation.findNavController(this).navigate(navDir)
}
