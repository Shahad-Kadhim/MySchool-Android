package com.shahad.app.my_school.ui.postDetails

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostDetailsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment: BaseFragment<FragmentPostDetailsBinding>() {

    override fun getLayoutId() = R.layout.fragment_post_details
    override val viewModel: PostDetailsViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.comments.adapter =
            CommentAdapterRecycler(
                viewModel.postDetails.value?.toData()?.data?.comments ?: emptyList(),
                viewModel
            )
        observe()
    }

    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@PostDetailsFragment){
                findNavController().navigateUp()
            }
        }
    }
}