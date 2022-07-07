package com.shahad.app.my_school.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "home")
        Button(onClick = { navController.navigate("profile"){
            popUpTo("home")
        } }) {
            Text(text = "GO TO Profile")
        }
    }
}


@Composable
fun AssignmentScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "assignment")
        Button(onClick = { navController.navigate("profile"){
            popUpTo("home")
        } }) {
            Text(text = "GO TO Profile")
        }
    }

}

@Composable
fun NotificationScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "notification")
        Button(onClick = { navController.navigate("profile"){
            popUpTo("home")
        } }) {
            Text(text = "GO TO Profile")
        }
    }

}
@Composable
fun ProfileScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "profile")
        Button(onClick = { navController.navigate("home"){
            popUpTo("profile")
        } }) {
            Text(text = "GO TO Home")
        }
    }

}

