package com.shahad.app.my_school.ui.postDetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentPostDetailsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment: BaseFragment<FragmentPostDetailsBinding>() {

    override fun getLayoutId() = R.layout.fragment_post_details
    override val viewModel: PostDetailsViewModel by viewModels()
    val args: PostDetailsFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        viewDataBinding.role = args.role
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
            clickSolutionEvent.observeEvent(this@PostDetailsFragment){
                viewDataBinding.root.goToFragment(
                    PostDetailsFragmentDirections.actionPostDetailsFragmentToSolutionFragment(it)
                )
            }
        }
    }
}