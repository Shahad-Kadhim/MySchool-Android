package com.shahad.app.my_school.ui.identity

import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.ActivityIdentityBinding
import com.shahad.app.my_school.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentityActivity : BaseActivity<ActivityIdentityBinding>() {

    override fun getLayoutId() = R.layout.activity_identity
    override val viewModel: IdentityViewModel by viewModels()

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.fragment_host_main).navigateUp()
        return true
    }

}