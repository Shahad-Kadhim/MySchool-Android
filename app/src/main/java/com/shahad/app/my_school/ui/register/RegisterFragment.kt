package com.shahad.app.my_school.ui.register

import android.os.Bundle
import android.view.View
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
        observeEvents()
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