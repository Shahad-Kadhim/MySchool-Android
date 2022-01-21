package com.shahad.app.my_school.ui.identity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityIdentityBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.isTrue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class IdentityActivity : BaseActivity<ActivityIdentityBinding>() {

    override fun getLayoutId() = R.layout.activity_identity
    override val viewModel: IdentityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        checkIfAlreadyLogin()
        super.onCreate(savedInstanceState)
    }

    private fun checkIfAlreadyLogin(){
        lifecycleScope.launchWhenCreated {
            viewModel.isAuth.collect { isAuth->
                takeIf {isAuth.isTrue()}?.let {
                    navToHome()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host_main).navigateUp()
        return true
    }

    fun onAuth(token: String){
        saveToken(token)
        navToHome()
    }

     private fun saveToken(token: String) {
         viewModel.storeToken(token)
    }

    private fun navToHome() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

}