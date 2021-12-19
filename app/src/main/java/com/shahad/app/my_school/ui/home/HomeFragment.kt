package com.shahad.app.my_school.ui.home

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

}