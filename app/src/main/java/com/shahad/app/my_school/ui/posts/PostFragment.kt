package com.shahad.app.my_school.ui.posts

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.register.Role
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment(val role: Role): BaseFragment<FragmentPostBinding>() {

    override fun getLayoutId() = R.layout.fragment_post
    override val viewModel: PostViewModel by viewModels()

    override fun onStart() {
        super.onStart()

        viewDataBinding.isAuth = role == Role.TEACHER
    }
}