package com.shahad.app.my_school.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.register.Role

@Composable
fun HomeScreen(navController: NavController, role: Role) {
    when(role){
        Role.TEACHER -> TeacherHome(navController)
        Role.STUDENT -> StudentHome(navController)
        Role.MANGER -> MangerHome(navController)
    }
}

@Composable
fun StudentHome(navController: NavController){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = { AppBar(title = "My School") }
    ){

    }
}

@Composable
fun AppBar(title: String){
    TopAppBar(
        title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold)),
                    ),
                )
                },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ,
        backgroundColor = colorResource(id = R.color.brand_color),
        contentColor = Color.White,
        actions = {
            Image(
                painter = painterResource(R.drawable.profile_photo),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
            )
        }

    )
}
@Composable
fun TeacherHome(navController: NavController) {

}

@Composable
fun MangerHome(navController: NavController) {

}

@Composable
fun AssignmentScreen(navController: NavController, role: Role) {

    AppBar("Assignment")
}
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
fun ProfileScreen(navController: NavController, role: Role) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "profile")
        Button(onClick = { navController.navigate("home"){
            popUpTo("profile")
        } }) {
            Text(text = "GO TO Home")
        }
    }

}

