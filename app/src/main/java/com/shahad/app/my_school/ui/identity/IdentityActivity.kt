package com.shahad.app.my_school.ui.identity

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityIdentityBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.extension.toRole
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentityActivity : BaseActivity<ActivityIdentityBinding>() {

    override fun getLayoutId() = R.layout.activity_identity
    override val viewModel: IdentityViewModel by viewModels()

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host_main).navigateUp()
        return true
    }

    fun onAuth(identification: Pair<String,String>){
        saveToken(identification.second)
        saveRole(identification.first)
        navToHome(identification.first)
    }

    private fun saveRole(role: String) {
        viewModel.storeRole(role)
    }

    private fun saveToken(token: String) {
         viewModel.storeToken(token)
    }

    private fun navToHome(role: String) {
        startActivity(
            Intent(this, MainActivity::class.java)
                .putExtra("ROLE",role)
        )
        finish()
    }

}