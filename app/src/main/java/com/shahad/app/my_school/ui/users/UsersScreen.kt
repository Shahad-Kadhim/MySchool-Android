package com.shahad.app.my_school.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.AppBar
import com.shahad.app.my_school.ui.NoResultAnimation
import com.shahad.app.my_school.ui.StrokedCard
import com.shahad.app.my_school.ui.SwiperLayout
import com.shahad.app.my_school.ui.home.SearchBar
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.ui.users.students.StudentsViewModel


@Composable
fun UsersScreen(navController: NavController, usersType: Role, type: String,viewModel: BaseUsersViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = type) {
                navController.navigateUp()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add,"add class")
            }
        }
    ) {
        val schools by viewModel.schools.observeAsState()
        val users by viewModel.users.observeAsState()
        val search by viewModel.search.observeAsState()
        Column {

            SearchBar(
                value = search ?: "",
                streamData = viewModel.search,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)
            )
            schools?.let {
                ChipGroup(
                    items = it,
                    selectedItem = schools?.find { currentSchool ->
                        (viewModel.schoolId.value) == currentSchool.id
                   },
                ){ selectedSchool ->
                    viewModel.schoolName.postValue(selectedSchool.name)
                }
            }
            users?.let { state ->
                SwiperLayout(
                    onRefresh = {
                        viewModel.refreshState.postValue(true)
                    },
                    state = state,
                ) {
                    state.toData()?.data.takeUnless { it.isNullOrEmpty() }?.let { users ->
                        items(users) {
                            UserItem(user = it)
                        }
                    }
                }
                state.toData()?.data.takeIf { it.isNullOrEmpty() }?.let {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        NoResultAnimation(modifier = Modifier.fillMaxSize(0.8f), "No $type here ")
                    }
                }
            }
        }

    }
}


@Composable
fun UserItem(user: UserSelected){
    StrokedCard(
        onClick = {
            user.isSelected = !user.isSelected
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 4.dp)
    ){
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


@Composable
fun<T> Chip(
    item: T,
    name: String,
    isSelected: Boolean = false ,
    onSelected: (T) -> Unit ,
){
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(8f),
        color = if (isSelected) colorResource(id = R.color.brand_color) else Color.LightGray,
        contentColor = Color.White
    ){

        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelected(item)
                    }
                )
        ) {
            Text(
                text =name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ChipGroup(
    items: List<School>,
    selectedItem: School?,
    onSelected: (School) -> Unit
){
    LazyRow(
        modifier = Modifier.padding(16.dp,0.dp),
    ){
            items(items){ school ->
                Chip(
                    name = school.name,
                    item= school,
                    onSelected = onSelected,
                    isSelected = selectedItem == school
                )
        }
    }

}