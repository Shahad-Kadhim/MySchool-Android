package com.shahad.app.my_school.ui.profile

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentProfileBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding>() {

    override fun getLayoutId() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

}