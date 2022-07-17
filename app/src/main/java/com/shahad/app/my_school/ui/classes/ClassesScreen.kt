package com.shahad.app.my_school.ui.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.AppBar
import com.shahad.app.my_school.ui.ClassItem
import com.shahad.app.my_school.ui.NoResultAnimation

@Composable
fun ClassesScreen(navController: NavController, viewModel: ClassesMangerViewModel){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = "Classes") {
                navController.navigateUp()
            }
        }
    ) {
        val classes by viewModel.classes.observeAsState()
        classes.takeUnless { it.isNullOrEmpty() }?.let { _classes ->
            LazyColumn{
                items(_classes){
                    ClassItem(classM = it)
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            NoResultAnimation(modifier = Modifier.fillMaxSize(0.8f), "No Classes here ")
        }
    }
}