package com.shahad.app.my_school.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.NotificationDto
import com.shahad.app.my_school.ui.AppBar
import com.shahad.app.my_school.ui.StrokedCard
import com.shahad.app.my_school.ui.SwiperLayout


@Composable
fun NotificationScreen(navController: NavController, viewModel: NotificationViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = "Notification") {
                navController.navigateUp()
            }
        }
    ){
        val notification by viewModel.notification.observeAsState()
        notification?.let { state ->
            SwiperLayout(
                onRefresh = {
                    viewModel.refreshState.postValue(true)
                },
                state = state,
            ){
                state.toData()?.data?.let{ notifications ->
                    items(notifications){
                        NotificationItem(it)
                    }
                }
            }
        }

    }

}

@Composable
fun NotificationItem(notification: NotificationDto) {
    StrokedCard(
        onClick = {  }
    ) {
        Column (
            modifier = Modifier
                .padding(24.dp, 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = notification.title,
                style = TextStyle(
                    color = colorResource(id = R.color.shade_primary_color),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.content,
                style = TextStyle(
                    color = colorResource(id = R.color.shade_secondary_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                )
            )
        }
    }
}
