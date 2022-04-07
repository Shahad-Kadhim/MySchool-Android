package com.shahad.app.my_school.ui.postDetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDutyDetailsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DutyDetailsFragment: BaseFragment<FragmentDutyDetailsBinding>() {

    override fun getLayoutId() = R.layout.fragment_duty_details
    override val viewModel: DutyDetailsViewModel by viewModels()

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
            clickBackEvent.observeEvent(this@DutyDetailsFragment){
                findNavController().navigateUp()
            }
        }
    }
}