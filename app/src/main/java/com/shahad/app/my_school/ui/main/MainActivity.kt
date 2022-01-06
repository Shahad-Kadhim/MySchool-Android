package com.shahad.app.my_school.ui.main

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityMainBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() =  R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewDataBinding.navigation
            .setupWithNavController(findNavController(R.id.fragment_host))
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host).navigateUp()
        return true
    }


}