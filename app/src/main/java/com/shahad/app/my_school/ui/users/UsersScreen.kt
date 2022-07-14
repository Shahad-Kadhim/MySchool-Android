package com.shahad.app.my_school.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.shahad.app.my_school.ui.register.Role


@Composable
fun UsersScreen(navController: NavController, usersType: Role) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "assignment")
        Button(onClick = { navController.navigate("profile"){
            popUpTo("home")
        } }) {
            Text(text = "GO TO Profile")
        }
    }

}
