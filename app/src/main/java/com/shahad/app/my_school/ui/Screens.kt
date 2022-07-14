package com.shahad.app.my_school.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.util.State


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
fun ClassItem(
    classM: ClassM,
    showStage: Boolean = false
) {
    StrokedCard(
        onClick = {
            //TODO LATER
        },
        modifier =Modifier
            .fillMaxWidth()
        .wrapContentHeight()
        .padding(16.dp, 4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
        ){
            Column(
                modifier = Modifier.padding(24.dp,16.dp,0.dp,16.dp),
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
            Spacer(modifier = Modifier.weight(1f))
            showStage.takeIf { it }?.let {
                Text(
                    text = classM.stage.toString(),
                    style = TextStyle(
                        color = colorResource(id = R.color.brand_color),
                        fontSize = 64.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxSize().padding(32.dp,0.dp).align(Alignment.CenterVertically)
                )
            }
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
            .padding(8.dp, 8.dp, 0.dp, 0.dp)
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
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){
    Card(
        modifier = modifier
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
fun<T> SwiperLayout(
    onRefresh: () -> Unit,
    state: State<BaseResponse<T>?>?,
    modifier: Modifier =Modifier,
    content: LazyListScope.() -> Unit,
){
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false ),
        onRefresh = {
            onRefresh()
        },
        modifier = modifier
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
fun SchoolItem(school: School, modifier: Modifier){
    StrokedCard(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_school),
                contentDescription = "school icon",
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = school.name,
                style = TextStyle(
                    color = colorResource(id = R.color.shade_primary_color),
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun SectionTitle(title: String){
    Text(
        text = title,
        style = TextStyle(
            color = colorResource(id = R.color.text),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.source_sans_pro_regular))
        ),
        modifier = Modifier.padding(16.dp,8.dp,0.dp,0.dp),
    )

}