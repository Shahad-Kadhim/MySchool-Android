package com.shahad.app.my_school.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.*
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.register.Role


@Composable
fun ProfileScreen(navController: NavController, viewModel: BaseViewModel, role: Role) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            AppBar(title = "") {
                navController.navigateUp()
            }
        }
    ){
        when(role) {
            Role.TEACHER -> TeacherProfile(viewModel = viewModel as TeacherProfileViewModel)
            Role.STUDENT -> StudentProfile(viewModel = viewModel as StudentProfileViewModel)
            Role.MANGER -> MangerProfile(viewModel = viewModel as MangerProfileViewModel)
        }
    }
}

@Composable
fun StudentProfile(viewModel: StudentProfileViewModel){
    val info by viewModel.info.observeAsState()
    val schools by viewModel.schools.observeAsState()
    SwiperLayout(
        onRefresh = { /*TODO*/ },
        modifier = Modifier.fillMaxSize(),
        state = info,
    ){
        info?.toData()?.data?.name?.let { name ->
            item{
                ProfileHeader(userName = name )
            }
        }
        item{
            info?.toData()?.data?.let {
                InfoField(fieldTitle = "Phone", content = it.phone.toString())
                InfoField(fieldTitle = "Age", content = it.age.toString())
                InfoField(fieldTitle = "Stage", content = it.stage.toString())
            }
        }

        item{
            SectionTitle(title = "Schools")
        }
        schools.takeUnless { it.isNullOrEmpty() }?.let {
            items(it){school ->
                SchoolItem(school = school)
            }
        } ?: item {
            NoResultAnimation(modifier = Modifier.fillMaxSize(),"No Schools here")
        }

    }
}

@Composable
fun TeacherProfile(viewModel: TeacherProfileViewModel){
    val info by viewModel.info.observeAsState()
    val schools by viewModel.schools.observeAsState()
    SwiperLayout(
        onRefresh = { /*TODO*/ },
        modifier = Modifier,
        state = info,
    ){
        info?.toData()?.data?.name?.let { name ->
            item{
                ProfileHeader(userName = name )
            }
        }
        item{
            info?.toData()?.data?.let {
                InfoField(fieldTitle = "Phone", content = it.phone.toString())
                InfoField(fieldTitle = "Teaching Specialization", content = it.teachingSpecialization)
            }
        }
        item{
            SectionTitle(title = "Schools")
        }
        schools.takeUnless { it.isNullOrEmpty() }?.let {
            items(it){school ->
                SchoolItem(school = school)
            }
        } ?: item {
            NoResultAnimation(modifier = Modifier.fillMaxSize(),"No Schools here")
        }

    }
}

@Composable
fun MangerProfile(viewModel: MangerProfileViewModel){
    val info by viewModel.info.observeAsState()
    val schools by viewModel.schools.observeAsState()
    SwiperLayout(
        onRefresh = { /*TODO*/ },
        state = info,
    ){
        info?.toData()?.data?.name?.let { name ->
            item{
                ProfileHeader(userName = name )
            }
        }
        item{
            info?.toData()?.data?.let {
                InfoField(fieldTitle = "Phone", content = it.phone)
            }
        }

        item{
            SectionTitle(title = "Schools")
        }
        schools.takeUnless { it.isNullOrEmpty() }?.let {
            items(it){school ->
                SchoolItem(school = school)
            }
        } ?: item {
            NoResultAnimation(modifier = Modifier.fillMaxSize(),"No Schools here")
        }

    }
}

@Composable
fun ProfileHeader(userName: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_photo),
            contentDescription = "profile photo",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(CircleShape)
                .border(2.dp, colorResource(id = R.color.brand_color), CircleShape)
        )

        Text(
            text = userName,
            style = TextStyle(
                color = colorResource(id = R.color.shade_primary_color),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun LazyListScope.SchoolRecycle(schools: List<School>){
    schools.takeIf { it.isNotEmpty() }?.let { schools ->
        items(schools){ school->
            SchoolItem(school = school)
        }
    }
}

@Composable
fun InfoField(fieldTitle: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
    ) {
        Text(
            text = fieldTitle,
            style = TextStyle(
                color = colorResource(id = R.color.shade_primary_color),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8f))
                .border(
                    1.dp,
                    colorResource(id = R.color.shade_primary_color),
                    RoundedCornerShape(8f)
                )
                .padding(8.dp),
            style = TextStyle(
                color = colorResource(id = R.color.shade_primary_color),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
            )
        )

    }
}
