package com.shahad.app.my_school.ui.members

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMemberBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.posts.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberFragment: BaseFragment<FragmentMemberBinding>() {

    override fun getLayoutId() = R.layout.fragment_member
    override val viewModel: PostViewModel by viewModels()

}