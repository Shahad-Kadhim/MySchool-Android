package com.shahad.app.my_school.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@HomeFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
        }
    }

}