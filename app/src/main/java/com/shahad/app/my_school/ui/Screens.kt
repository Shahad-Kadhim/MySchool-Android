package com.shahad.app.my_school.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.AssignmentDto
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.duty.BaseAssignmentViewModel
import com.shahad.app.my_school.ui.home.student.HomeStudentViewModel
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.State

@Composable
fun HomeScreen(navController: NavController, role: Role,viewModel: BaseViewModel) {
    when(role){
        Role.TEACHER -> TeacherHome(navController)
        Role.STUDENT -> StudentHome(navController,viewModel as HomeStudentViewModel)
        Role.MANGER -> MangerHome(navController)
    }
}

@Composable
fun StudentHome(navController: NavController, viewModel: HomeStudentViewModel){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color)),
        topBar = {
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
                    Text(
                        text = "Classes",
                        style = TextStyle(
                            color = colorResource(id = R.color.text),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
                        ),
                        modifier = Modifier.padding(16.dp,0.dp,0.dp,0.dp)
                    )
                }
                classes?.takeIf { it.isNotEmpty() }?.let{ classesList ->
                    items(classesList){
                        ClassItem(it)
                    }
                } ?: item {
                    NoResultAnimation(
                        Modifier.fillMaxWidth().padding(0.dp, 64.dp, 0.dp, 0.dp),
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
                .padding(16.dp)
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
fun BasicLottie(
    @RawRes lottieId: Int,
    modifier: Modifier =Modifier
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId))
        LottieAnimation(
            composition = composition,
            isPlaying = true,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
                .width(172.dp)
                .height(172.dp),
        )
    }
}

@Composable
fun NoResultAnimation(
    modifier: Modifier,
    label: String,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicLottie(
            lottieId = R.raw.no_result,
            modifier = modifier
        )
        Text(
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
            ),
        )
    }
}
@Composable
fun LoadingAnimation(){
    BasicLottie(lottieId = R.raw.loading)
}

@Composable
fun ErrorConnectionAnimation(){
    BasicLottie(lottieId = R.raw.error_connection)
}

@Composable
fun ErrorAnimation(){
    BasicLottie(lottieId = R.raw.error)
}

@Composable
fun ClassItem(classM: ClassM) {
    StrokedCard(
        onClick = {
            //TODO LATER
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp, 16.dp)
                .wrapContentHeight(Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = classM.name,
                style = TextStyle(
                    color = colorResource(id = R.color.class_text),
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                )
            )
            Text(
                text = " by Mr. ${classM.teacherName}",
                style = TextStyle(
                    color = colorResource(id = R.color.secondery_color),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                )
            )
        }
    }
}

@Composable
fun AppBar(
    title: String,
    onClickBack: () -> Unit,
){
    TopAppBar(
        title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_semi_bold)),
                    ),
                )
                },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 16.dp, 0.dp, 0.dp)
        ,
        contentColor = colorResource(id = R.color.black),
        navigationIcon = {
            IconButton(onClick = {onClickBack()}){
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .width(24.dp)
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation =0.dp
    )
}

@Composable
fun StrokedCard(
    onClick: () -> Unit,
    content: @Composable () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 4.dp)
            .border(
                1.dp,
                color = colorResource(id = R.color.stroke_color),
                shape = RoundedCornerShape(24f)
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(24f),
        content = content
    )
}

@Composable
fun TeacherHome(navController: NavController) {

}

@Composable
fun MangerHome(navController: NavController) {

}

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
fun<T> SwiperLayout(
    onRefresh: () -> Unit,
    state: State<T>,
    modifier: Modifier =Modifier,
    content: LazyListScope.() -> Unit,
){
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false ),
        onRefresh = {
            onRefresh()
        },
        modifier =modifier
    ){
        when(state){
            State.ConnectionError -> ErrorConnectionAnimation()
            is State.Error -> ErrorAnimation()
            State.Loading -> LoadingAnimation()
            is State.Success -> {
                LazyColumn(
                    content = content,
                )
            }
            State.UnAuthorization -> {}
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
                    )                )
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

