package com.shahad.app.my_school.ui.posts

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment: BaseFragment<FragmentPostBinding>() {

    override fun getLayoutId() = R.layout.fragment_post
    override val viewModel: PostViewModel by viewModels()

}