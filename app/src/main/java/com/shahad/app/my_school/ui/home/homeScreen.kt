package com.shahad.app.my_school.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.*
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.home.manger.HomeMangerViewModel
import com.shahad.app.my_school.ui.home.student.HomeStudentViewModel
import com.shahad.app.my_school.ui.home.teacher.HomeTeacherViewModel
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.State


@Composable
fun HomeScreen(navController: NavController, role: Role, viewModel: BaseViewModel) {
    when(role){
        Role.TEACHER -> TeacherHome(navController, viewModel as HomeTeacherViewModel)
        Role.STUDENT -> StudentHome(navController,viewModel as HomeStudentViewModel)
        Role.MANGER -> MangerHome(navController, viewModel as HomeMangerViewModel)
    }
}

@Composable
fun StudentHome(navController: NavController, viewModel: HomeStudentViewModel){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = { HomeAppBar(navController = navController) },
    ){
        val duty by viewModel.dutiesStatistic.observeAsState()
        val classes by viewModel.classes.observeAsState()
        val isRefreshing by viewModel.refreshState.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing ),
            onRefresh = {
                viewModel.refreshClasses()
            }
        ) {
            LazyColumn {
                item {
                    Duties(navController,duty)
                   SectionTitle(title = "Classes")
                }
                classes?.takeIf { it.isNotEmpty() }?.let{ classesList ->
                    items(classesList){
                        ClassItem(it)
                    }
                } ?: item {
                    NoResultAnimation(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 64.dp, 0.dp, 0.dp),
                        "No Classes Here",
                    )
                }
            }
        }
    }
}



@Composable
fun Duties(navController: NavController, state: State<BaseResponse<String>?>?) {
    if(state is State.Success){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .clickable {
                    navController.navigate(Screen.Assignment.route)
                },
            backgroundColor = colorResource(id = R.color.brand_color),
            shape = RoundedCornerShape(24f)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)) {
                Column(
                    Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Today",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
                        )
                    )
                    Text(
                        text = state.data?.data ?: "No tasks for today",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold))
                        ),
                        modifier = Modifier.padding(0.dp,8.dp,0.dp,0.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_frame_2608173),
                    contentDescription = "",
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier.padding(0.dp,0.dp,8.dp,0.dp)
                )
            }
        }
    }
}


@Composable
fun TeacherHome(navController: NavController, viewModel: HomeTeacherViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            HomeAppBar(navController = navController){
                Image(
                    painter = painterResource(R.drawable.ic_school),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
//                            navController.navigate(Screen.Profile.route)
                        },
                    colorFilter = ColorFilter.tint(Color.White)
                )
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
    ){
        val classes by viewModel.classes.observeAsState()
        val isRefreshing by viewModel.refreshState.collectAsState()
        val searchKey by viewModel.search.observeAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing ),
            onRefresh = {
                viewModel.refreshClasses()
            }
        ) {
            Column {
                SearchBar(
                    searchKey,
                    viewModel.search,
                    Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
                )
                LazyColumn {
                    item{
                        SectionTitle(title = "Classes")
                    }
                    classes?.takeIf { it.isNotEmpty() }?.let{ classesList ->
                        items(classesList){
                            ClassItem(it,true)
                        }
                    } ?: item {
                        NoResultAnimation(
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 64.dp, 0.dp, 0.dp),
                            "No Classes Here",
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun MangerHome(navController: NavController, viewModel: HomeMangerViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
            HomeAppBar(navController = navController){
                Image(
                    painter = painterResource(R.drawable.ic_notifaction),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navController.navigate(Screen.Notification.route)
                        },
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = colorResource(id = R.color.brand_color),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add,"add school")
            }
        }
    ){
        val classes by viewModel.classes.observeAsState()
        val schools by viewModel.schools.observeAsState()
        val isRefreshing by viewModel.refreshState.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing ),
            onRefresh = {
                viewModel.refreshClasses()
                viewModel.refreshSchools()
            }
        ) {
            LazyColumn {

                schools.takeUnless { it.isNullOrEmpty() }?.let { schools ->
                    item {
                        SchoolsItemsHeaders {
                        //TODO LATER
                        }
                        SchoolsRecycle(schools = schools)
                    }
                }

                item {
                    Buttons(navController = navController)
                }
                item{
                    SectionTitle(title = "Classes")
                }

                classes?.takeIf { it.isNotEmpty() }?.let{ classesList ->
                    items(classesList){
                        ClassItem(it,true)
                    }
                } ?: item {
                    NoResultAnimation(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 64.dp, 0.dp, 0.dp),
                        "No Classes Here",
                    )
                }
            }
        }


    }
}

@Composable
fun SchoolsItemsHeaders(
    onClickShowAll: () -> Unit
){
    Row(
        modifier = Modifier.padding(0.dp,4.dp,16.dp,0.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        SectionTitle(title = "Schools")
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "see all schools",
            textAlign = TextAlign.End,
            modifier = Modifier
                .clickable {
                    onClickShowAll()
                },
            style = TextStyle(
                color = colorResource(id = R.color.shade_primary_color),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
            ),
        )
    }
}

@Composable
fun SchoolsRecycle(schools: List<School>){
    LazyRow{
        item{
            Spacer(modifier = Modifier.width(12.dp))
        }
        items(schools){ school ->
            SchoolItem(
                school = school,
                modifier = Modifier
                    .wrapContentHeight()
                    .width(180.dp)
                    .padding(4.dp, 4.dp)
            )
        }
        item{
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
@Composable
fun HomeAppBar(
    navController: NavController,
    navigationButton: @Composable RowScope.() -> Unit = {}
){
    TopAppBar(
        title = {
            Text(
                text = "My School",
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
            navigationButton()
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.profile_photo),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Screen.Profile.route)
                    }
            )

        }

    )
}

@Composable
fun SearchBar(
    value: String?,
    streamData: MutableLiveData<String?>,
    modifier: Modifier
){

    Row(
        modifier = modifier //TODO Change padding top later
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(R.color.search_background))
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search icon",
            modifier =Modifier
                .padding(16.dp, 0.dp)
        )

        BasicTextField(
            value = value ?: "",
            onValueChange = {
                streamData.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(0.dp, 12.dp),
            textStyle = TextStyle(
                color =  colorResource(id = R.color.shade_primary_color),
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                fontSize = 16.sp
            ),
            singleLine = true,
        )
    }
}

@Composable
fun Buttons(navController: NavController){
        val constraint = ConstraintSet {
            val navToStudents= createRefFor("navToStudents")
            val navToTeachers= createRefFor("navToTeachers")
            val navToSchools= createRefFor("navToSchools")
            val navToClasses= createRefFor("navToClasses")
            constrain(navToStudents){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            constrain(navToTeachers){
                start.linkTo(navToStudents.end)
                top.linkTo(parent.top)
            }
            constrain(navToSchools){
                start.linkTo(navToTeachers.end)
                top.linkTo(parent.top)
            }
            constrain(navToClasses){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
            createHorizontalChain(navToStudents,navToTeachers,navToSchools,navToClasses)
        }
        ConstraintLayout(
            constraint,
            Modifier
                .padding(0.dp,16.dp,0.dp,8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
               NavigationButton(
                onClick = {
                    navController.navigate("students")
                },
                icon = R.drawable.ic_student,
                modifier =   Modifier.layoutId("navToStudents")
            )

            NavigationButton(
                onClick = {
//                    navController.navigate("teachers") TODO LATER
                },
                icon = R.drawable.ic_teacher_2,
                modifier= Modifier.layoutId("navToTeachers")

            )
            NavigationButton(
                onClick = {
//                    navController.navigate("schools") TODO LATER
                },
                icon = R.drawable.ic_school,
                Modifier.layoutId("navToSchools")

            )
            NavigationButton(
                onClick = {
//                    navController.navigate("classes") TODO LATER
                },
                icon = R.drawable.ic_teacher,
                Modifier.layoutId("navToClasses")

            )
    }
}

@Composable
fun NavigationButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .width(84.dp)
            .height(84.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(25),
        backgroundColor = colorResource(id = R.color.search_background)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = colorResource(id = R.color.brand_color),
            modifier = Modifier.padding(16.dp).fillMaxSize()
        )
    }
}