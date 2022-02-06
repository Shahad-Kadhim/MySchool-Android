package com.shahad.app.my_school.ui.register

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
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

    @ExperimentalPagerApi
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
            ChooseRole(viewModel.role)
            when(viewModel.role.observeAsState().value){
                Role.TEACHER -> {
                    Form1(
                        @Composable{NameAndPasswordField()},
                        @Composable{PhoneField()},
                        @Composable{
                            EditTextField(
                                modifier = Modifier.padding(top = 16.dp),
                                value = viewModel.teachingSpecialization.observeAsState().value ?: "",
                                hint = "teachingSpecialization",
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
                        @Composable{NameAndPasswordField()},
                        @Composable{PhoneField()},
                        @Composable{
                            EditTextField(
                                modifier = Modifier.padding(top = 16.dp),
                                value = (viewModel.age.observeAsState().value ?: "").toString(),
                                hint = "age",
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
                                modifier = Modifier.padding(top = 16.dp),
                                value = (viewModel.stage.observeAsState().value ?: "").toString(),
                                hint = "stage",
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
                                modifier = Modifier.padding(top = 16.dp),
                                value = viewModel.note.observeAsState().value ?: "",
                                hint = "note",
                                onchange = { value ->
                                    viewModel.note.value =if(value.isNotBlank()) value else null
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                        },
                    )
                }
                Role.MANGER -> {
                  Form1(@Composable{NameAndPasswordField()})
                }
            }
        }
    }

    @Composable
    private fun Form1( vararg content: @Composable() () -> Unit) {
        Box(
            Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
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
    fun NameAndPasswordField(){
        Column {
            EditTextField(
                modifier = Modifier.padding(top = 16.dp),
                value = (viewModel.name.observeAsState().value) ?: "" ,
                hint = "name",
                onchange = { value->
                    viewModel.name.value =if(value.isNotBlank()) value else null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            EditTextField(
                modifier = Modifier.padding(top = 16.dp),
                value = (viewModel.password.observeAsState().value) ?: "",
                hint = "password",
                onchange = { value->
                    viewModel.password.value =if(value.isNotBlank()) value else null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }

    @Composable
    fun PhoneField(){
        EditTextField(
            modifier = Modifier.padding(top = 16.dp),
            value = (viewModel.phone.observeAsState().value ?: "").toString(),
            hint = "phone",
            onchange = { value->
                value.toLongOrNull().let{
                    viewModel.phone.value = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
    }

    @Composable
    private fun ChooseRole(roleState: MutableLiveData<Role>) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .height(24.dp)
        ){
            CustomRadioButton(roleState = roleState, role = Role.TEACHER)
            Spacer(modifier = Modifier.size(4.dp))
            CustomRadioButton(roleState = roleState, role = Role.STUDENT)
            Spacer(modifier = Modifier.size(4.dp))
            CustomRadioButton(roleState = roleState, role = Role.MANGER)
        }
    }

    @Composable
    private fun CustomRadioButton(roleState: MutableLiveData<Role>, role: Role){
        Row{
            RadioButton(
                selected = roleState.observeAsState().value == role,
                onClick = { roleState.postValue(role) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.brand_color),
                    unselectedColor = colorResource(R.color.shade_primary_color)
                )
            )
            Text(
                text = role.name,
                modifier = Modifier
                    .clickable(onClick = { roleState.postValue(role) })
                    .padding(start = 2.dp),
                style = TextStyle(
                    color = takeIf {roleState.observeAsState().value == role}?.let {
                        colorResource(R.color.brand_color)
                    } ?: colorResource(R.color.shade_primary_color)
                )
            )
        }
    }

    @Composable
    private fun EditTextField(
        modifier: Modifier = Modifier,
        hint: String,
        value: String,
        onchange:(String) -> Unit,
        keyboardOptions : KeyboardOptions
    ){
        TextField(
            value = value ,
            onValueChange = onchange,
            textStyle = TextStyle(
                color =  colorResource(R.color.shade_secondary_color),
                fontFamily = FontFamily(Font(R.font.monda_regular400)),
                fontSize = 12.sp
            ),
            keyboardOptions = keyboardOptions,
            placeholder = @Composable{
                 Text(
                     text = hint,
                     color =  colorResource(R.color.shade_secondary_color),
                     style = TextStyle(
                         color =  colorResource(R.color.shade_secondary_color),
                         fontFamily = FontFamily(Font(R.font.monda_regular400)),
                         fontSize = 12.sp
                     ),
                     modifier=Modifier.fillMaxSize()
                 )
            },
            shape = RoundedCornerShape(8.dp) ,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(R.color.card_background_color),
                cursorColor = colorResource(R.color.shade_secondary_color),
                disabledLabelColor = colorResource(R.color.card_background_color),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
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