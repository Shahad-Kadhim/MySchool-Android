package com.shahad.app.my_school.ui.main

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityMainBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import com.shahad.app.my_school.ui.identity.IdentityActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() =  R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.navigation
            .setupWithNavController(findNavController(R.id.fragment_host))
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host).navigateUp()
        return true
    }

    fun navToIdentity(){
        startActivity(Intent(this, IdentityActivity::class.java))
        finish()
    }
}