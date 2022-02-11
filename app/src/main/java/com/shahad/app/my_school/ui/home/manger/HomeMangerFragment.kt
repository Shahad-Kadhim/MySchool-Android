package com.shahad.app.my_school.ui.home.manger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMangerHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMangerFragment: BaseFragment<FragmentMangerHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_manger_home
    override val viewModel: HomeMangerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG","MANGER")
        observe()
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@HomeMangerFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
        }
    }

}