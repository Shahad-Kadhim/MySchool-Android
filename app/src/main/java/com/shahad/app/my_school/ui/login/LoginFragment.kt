package com.shahad.app.my_school.ui.login

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentLoginBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override fun getLayoutId() = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

}