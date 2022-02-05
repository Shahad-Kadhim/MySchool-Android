package com.shahad.app.my_school.ui.register

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
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
                    BasicForm(
                        mapOf(
                            Pair("name", viewModel.name),
                            Pair("password", viewModel.password),
                            Pair("phone", viewModel.phone),
                            Pair("Teaching Specialization", viewModel.teachingSpecialization)
                        )
                    )
                }
                Role.STUDENT -> {
                    BasicForm(
                        mapOf(
                            Pair("name",viewModel.name),
                            Pair("password", viewModel.password),
                            Pair("phone", viewModel.phone),
                            Pair("age", viewModel.age),
                            Pair("stage", viewModel.stage),
                            Pair("note", viewModel.note),
                        )
                    )
                }
                Role.MANGER -> {
                    BasicForm(
                        mapOf(
                            Pair("name",viewModel.name),
                            Pair("password", viewModel.password),
                        )
                    )
                }
            }
        }
    }
    @Composable
    private fun BasicForm(list: Map<String,MutableLiveData<String?>>){
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
                list.forEach {
                    EditTextField(
                        modifier = Modifier.padding(top = 16.dp),
                        liveData = it.value,
                        hint = it.key
                    )
                }
            }
        }
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
        liveData: MutableLiveData<String?>,
        hint: String
    ){
        TextField(
            value = liveData.observeAsState().value ?: "",
            onValueChange = {
               liveData.postValue(it)
            },
            textStyle = TextStyle(
                color =  colorResource(R.color.shade_secondary_color),
                fontFamily = FontFamily(Font(R.font.monda_regular400)),
                fontSize = 12.sp
            ),
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