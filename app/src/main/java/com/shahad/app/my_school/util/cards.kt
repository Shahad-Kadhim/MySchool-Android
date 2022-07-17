package com.shahad.app.my_school.util


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.users.UserItem


@Composable
fun UsersRecycle(users: List<UserSelected>){
    LazyColumn{
        items(users){
            UserItem(user = it)
        }
    }
}
