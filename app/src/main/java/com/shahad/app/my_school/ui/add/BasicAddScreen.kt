package com.shahad.app.my_school.ui.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahad.app.my_school.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    viewModel: BaseNewViewModel,
    type: String,
    scope: CoroutineScope,
    content: @Composable (ModalBottomSheetState) -> Unit,
){
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val name by viewModel.name.observeAsState()
    ModalBottomSheetLayout(
        sheetContent = {
            Column {
                Text(
                    text = "New $type",
                    style = TextStyle(
                        color = colorResource(id = R.color.shade_primary_color),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                    ),
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp,top= 24.dp)
                    )


                TextField(
                    value = name ?: "" ,
                    onValueChange = {
                                    viewModel.name.value = it
                    },
                    textStyle = TextStyle(
                        color =  Color(50,54,87),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(24.dp, 8.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(R.color.card_background_color),
                        cursorColor = Color(225, 227, 232, 255),
                        disabledLabelColor = colorResource(R.color.card_background_color),
                        focusedIndicatorColor = Color(225, 227, 232, 255),
                        unfocusedIndicatorColor = Color(225, 227, 232, 255)
                    )
                )
                Button(
                    onClick = {
                        viewModel.onClickAdd()
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.brand_color))

                ) {
                    Text(
                        text ="Create",
                        style = TextStyle(
                            color =  Color.White,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                            fontSize = 18.sp
                        )
                    )
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(32.dp,32.dp,0.dp,0.dp)
    ){
        content(modalBottomSheetState)
    }
}