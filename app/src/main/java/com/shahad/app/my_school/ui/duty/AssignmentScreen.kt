package com.shahad.app.my_school.ui.duty

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
import com.shahad.app.my_school.data.remote.response.AssignmentDto
import com.shahad.app.my_school.ui.*
import com.shahad.app.my_school.ui.register.Role


@Composable
fun AssignmentScreen(navController: NavController, role: Role, viewModel: BaseAssignmentViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = "Assignment") {
                navController.navigateUp()
            }
        }
    ){
        val oo = viewModel.assignments.value
        oo?.let {
            val assignment by oo.observeAsState()
            assignment?.let {
                SwiperLayout(
                    onRefresh = { viewModel.refreshState.postValue(true) },
                    modifier = Modifier.fillMaxSize(),
                    state = it
                ){
                    it.toData()?.data.takeUnless { it.isNullOrEmpty() }?.let {
                        items(it){
                            AssignmentItem(it)
                        }
                    } ?: item {
                        NoResultAnimation(modifier = Modifier.fillMaxSize(),"No Assignment Yet")
                    }
                }
            }
        }
    }
}


@Composable
fun AssignmentItem(assignment: AssignmentDto) {
    StrokedCard(
        onClick = { /*TODO*/ }
    ) {
        Column(
            Modifier
                .padding(24.dp, 16.dp)
                .fillMaxSize()){
            Row{
                Text(
                    text = assignment.title,
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = assignment.datePosted.toString(),
                    style = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                    )
                )
            }
            Text(
                text = assignment.content,
                style = TextStyle(
                    color = colorResource(id = R.color.secondery_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                )
            )
        }
    }
}
