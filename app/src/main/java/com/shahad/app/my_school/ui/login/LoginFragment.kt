package com.shahad.app.my_school.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentLoginBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.identity.IdentityActivity
import com.shahad.app.my_school.util.goToFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override fun getLayoutId() = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        with(viewModel){
            lifecycleScope.launchWhenStarted {

                clickNavSignUpEvent.collect {
                    it?.let {
                        viewDataBinding.root.goToFragment(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                    }
                }
            }

            whenSuccess.observe(
                this@LoginFragment,
                (requireActivity() as IdentityActivity)::onAuth
            )
        }
    }

}