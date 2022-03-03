package com.shahad.app.my_school.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityMainBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import com.shahad.app.my_school.ui.identity.IdentityActivity
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.toRole
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() =  R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        determineStartDestination()
    }

    private fun determineStartDestination() {
        lifecycleScope.launchWhenStarted {
            (intent.getStringExtra("ROLE") ?: viewModel.role.first()).let {
                if(it ==null){
                    navToIdentity()
                }else{
                    with(it.toRole()){
                        setStartDestination(this)
                        setBottomNavigation(this)
                    }
                }
            }
        }
    }

    private fun setStartDestination(role: Role){
        findNavController(R.id.fragment_host).apply {
            graph=
                when(role){
                    Role.TEACHER -> navInflater.inflate(R.navigation.teacher_nav)
                    Role.STUDENT -> navInflater.inflate(R.navigation.student_nav)
                    Role.MANGER -> navInflater.inflate(R.navigation.manger_nav)
                }
        }
    }

    private fun setBottomNavigation(role: Role) {
        viewDataBinding.navigation.apply {
            when(role){
                    Role.TEACHER -> inflateMenu(R.menu.teacher_menu)
                    Role.STUDENT -> inflateMenu(R.menu.student_menu)
                    Role.MANGER -> inflateMenu(R.menu.manger_menu)
                }
            setupWithNavController(findNavController(R.id.fragment_host))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host).navigateUp()
        return true
    }

    override fun onBackPressed() {
        if(!findNavController(R.id.fragment_host).navigateUp()) super.onBackPressed()
    }

    fun navToIdentity(){
        startActivity(Intent(this, IdentityActivity::class.java))
        finish()
    }
}