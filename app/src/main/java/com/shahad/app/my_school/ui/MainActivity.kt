package com.shahad.app.my_school.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahad.app.my_school.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home"){
                composable("home"){
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "home")
                        Button(onClick = { navController.navigate("profile"){
                            popUpTo("home")
                        } }) {
                            Text(text = "GO TO Profile")
                        }
                    }
                }
                composable("profile"){
                    Text(text = "profile")
                }
                composable("search"){
                    Text(text = "search")
                }
            }


        }
    }

}