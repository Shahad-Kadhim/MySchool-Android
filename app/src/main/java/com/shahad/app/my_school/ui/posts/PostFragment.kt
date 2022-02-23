package com.shahad.app.my_school.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.register.Role
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment: BaseFragment<FragmentPostBinding>() {

    override fun getLayoutId() = R.layout.fragment_post
    override val viewModel: PostViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.isAuth = (arguments?.getSerializable("ROLE") as Role) == Role.TEACHER
    }

}