package com.shahad.app.my_school.ui.postDetails

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostDetailsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment: BaseFragment<FragmentPostDetailsBinding>() {

    override fun getLayoutId() = R.layout.fragment_post_details
    override val viewModel: PostDetailsViewModel by viewModels()

}