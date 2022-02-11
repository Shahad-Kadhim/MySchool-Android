package com.shahad.app.my_school.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
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
                    val startDestination =
                        when(it.toRole()){
                            Role.TEACHER -> R.id.teacher_nav
                            Role.STUDENT -> R.id.student_nav
                            Role.MANGER -> R.id.manger_nav
                        }
                    setStartDestination(startDestination)
                    setBottomNavigation()
                }
            }
        }
    }

    private fun setStartDestination(startDestination: Int){
        findNavController(R.id.fragment_host).apply {
            graph=navInflater.inflate(R.navigation.main_nav_graph).apply {
                setStartDestination(startDestination)
            }
        }
    }

    private fun setBottomNavigation() {
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