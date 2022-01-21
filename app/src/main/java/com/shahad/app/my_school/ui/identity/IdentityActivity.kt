package com.shahad.app.my_school.ui.identity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shahad.app.my_school.PreferencesKeys
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityIdentityBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.writeTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentityActivity : BaseActivity<ActivityIdentityBinding>() {

    override fun getLayoutId() = R.layout.activity_identity
    override val viewModel: IdentityViewModel by viewModels()

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host_main).navigateUp()
        return true
    }

    fun onAuth(token: String){
        saveToken(token)
        navToHome()
    }

     private fun saveToken(token: String) {
        lifecycleScope.launchWhenStarted {
            writeTo(PreferencesKeys.Token, token)
        }
    }

    private fun navToHome() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

}