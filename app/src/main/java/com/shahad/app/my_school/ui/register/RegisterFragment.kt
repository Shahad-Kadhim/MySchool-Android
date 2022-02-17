package com.shahad.app.my_school.ui.register

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentRegisterBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.identity.IdentityActivity
import com.shahad.app.my_school.util.extension.goToFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {

    override fun getLayoutId() = R.layout.fragment_register
    override val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.form.setContent {
            Form()
        }
        observeEvents()
    }

    @Composable
    private fun Form() {
        Column {
            when(viewModel.role.observeAsState().value){
                Role.TEACHER -> {
                    Form1(
                        @Composable{NameAndPasswordAndPhoneField()},
                        @Composable{
                            EditTextField(
                                value = viewModel.teachingSpecialization.observeAsState().value ?: "",
                                label = "teachingSpecialization",
                                onchange = { value->
                                    viewModel.teachingSpecialization.value =if(value.isNotBlank()) value else null
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                        }
                    )
                }
                Role.STUDENT -> {
                    Form1(
                        @Composable{NameAndPasswordAndPhoneField()},
                        @Composable{
                            EditTextField(
                                value = (viewModel.age.observeAsState().value ?: "").toString(),
                                label = "Age",
                                onchange = { value ->
                                    value.toIntOrNull().let {
                                        viewModel.age.postValue(it)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        },
                        @Composable{
                            EditTextField(
                                value = (viewModel.stage.observeAsState().value ?: "").toString(),
                                label = "Stage",
                                onchange = { value ->
                                    value.toIntOrNull().let {
                                        viewModel.stage.postValue(it)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        },
                        @Composable{
                            EditTextField(
                                value = viewModel.note.observeAsState().value ?: "",
                                label = "Note",
                                onchange = { value ->
                                    viewModel.note.value =if(value.isNotBlank()) value else null
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                        },
                    )
                }
                Role.MANGER -> {
                  Form1(@Composable{NameAndPasswordAndPhoneField()})
                }
            }
        }
    }

    @Composable
    private fun Form1( vararg content: @Composable () -> Unit) {
        Box(
            Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .wrapContentHeight()
        ){
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                content.forEach {
                    it()
                }

            }
        }
    }

    @Composable
    fun NameAndPasswordAndPhoneField(){
        Column {
            EditTextField(
                value = (viewModel.name.observeAsState().value) ?: "" ,
                label = "Name",
                onchange = { value->
                    viewModel.name.value =if(value.isNotBlank()) value else null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            EditTextField(
                value = (viewModel.password.observeAsState().value) ?: "",
                label = "Password",
                onchange = { value->
                    viewModel.password.value =if(value.isNotBlank()) value else null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            EditTextField(
                value = (viewModel.phone.observeAsState().value ?: "").toString(),
                label = "Phone",
                onchange = { value->
                    value.toLongOrNull().let{
                        viewModel.phone.value = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }
    }

    @Composable
    private fun EditTextField(
        modifier: Modifier = Modifier,
        label: String,
        value: String,
        onchange:(String) -> Unit,
        keyboardOptions : KeyboardOptions
    ){
        TextField(
            value = value ,
            onValueChange = onchange,
            textStyle = TextStyle(
                color =  Color(50,54,87),
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                fontSize = 16.sp
            ),
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(8.dp) ,
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(R.color.card_background_color),
                cursorColor = Color(225, 227, 232, 255),
                disabledLabelColor = colorResource(R.color.card_background_color),
                focusedIndicatorColor = Color(225, 227, 232, 255),
                unfocusedIndicatorColor = Color(225, 227, 232, 255)
            ),
            label = { TextLabel(label) }
        )
    }

    @Composable
    fun TextLabel(label: String){
        Text(
            text = label,
            style = TextStyle(
                color =  Color(165,165,165),
                fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                fontSize = 12.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    private fun observeEvents() {
        with(viewModel){
            lifecycleScope.launchWhenStarted {
                clickNavLoginEvent.collect {
                    it?.let {
                        viewDataBinding.root.goToFragment(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                    }
                }
            }
            whenSuccess.observe(
                this@RegisterFragment,
                (requireActivity() as IdentityActivity)::onAuth
            )
        }
    }

}