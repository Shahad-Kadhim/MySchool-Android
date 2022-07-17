package com.shahad.app.my_school.ui.schools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.AppBar
import com.shahad.app.my_school.ui.NoResultAnimation
import com.shahad.app.my_school.ui.SchoolItem

@Composable
fun SchoolScreen(navController: NavController, viewModel: SchoolBaseViewModel){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = "Schools") {
                navController.navigateUp()
            }
        },
        floatingActionButton = {
            if(viewModel is SchoolMangerViewModel){
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    backgroundColor = colorResource(id = R.color.brand_color),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add,"add school")
                }
            }
        }
    ) {
        val schools by viewModel.schools.observeAsState()
        schools.takeUnless { it.isNullOrEmpty() }?.let { _schools ->
            LazyColumn{
                items(_schools){
                    SchoolItem(
                        school = it,
                        modifier =  Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp, 4.dp)
                    )
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                NoResultAnimation(modifier = Modifier.fillMaxSize(0.8f), "No Schools here ")
            }
    }
}