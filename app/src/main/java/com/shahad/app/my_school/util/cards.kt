package com.shahad.app.my_school.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.mappers.UserSelected


@Composable
fun UsersRecycle(users: List<UserSelected>){
    LazyColumn{
        items(users){
            UserItem(user = it)
        }
    }
}

@Composable
fun UserItem(user: UserSelected){
    Card(
        shape = RoundedCornerShape(24f),
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, colorResource(id = R.color.stroke_color), RoundedCornerShape(24f))
            .background(colorResource(id = R.color.background_color))
            .fillMaxWidth()
            .clickable {
                user.isSelected = !user.isSelected
            }
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if(user.isSelected) { painterResource(id = R.drawable.selected_shape) } else painterResource(id = R.drawable.ic_user),
                contentDescription = "user icon",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(16.dp),
            )
            Text(
                text = user.name,
                style = TextStyle(
                    color = colorResource(id = R.color.text_primary_color)
                )
                )
        }


    }
}