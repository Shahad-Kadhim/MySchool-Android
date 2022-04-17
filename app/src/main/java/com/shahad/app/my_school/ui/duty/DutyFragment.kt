package com.shahad.app.my_school.ui.duty

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDutyBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.ui.solution.SolutionsAdapterRecycler
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DutyFragment: BaseFragment<FragmentDutyBinding>() {

    override fun getLayoutId() = R.layout.fragment_duty
    override val viewModel: DutyViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        observe()
        viewDataBinding.dutyRecycle.adapter = DutiesAdapterRecycler(listOf(),viewModel)
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@DutyFragment){
                findNavController().navigateUp()
            }
            clickDutyEvent.observeEvent(this@DutyFragment){
                viewDataBinding.root.goToFragment(
                    DutyFragmentDirections.actionAssignmentFragmentToPostDetailsFragment(it,Role.TEACHER)
                )
            }
        }
    }
}